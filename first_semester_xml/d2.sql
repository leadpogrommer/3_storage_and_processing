drop schema if exists xml cascade;
drop type if exists gender cascade;

CREATE type gender AS ENUM ('F', 'M');

create extension if not exists plpython3u;

create or replace function check_marriage() RETURNS TRIGGER language plpython3u AS $$
    new_row = TD["new"]
    plan = plpy.prepare("SELECT * FROM people WHERE id = $1;", ["int"])
    husband = plpy.execute(plan, [new_row["husband_id"]])
    wife = plpy.execute(plan, [new_row["wife_id"]])
    if len(husband) == 0:
        raise Exception("Husband does not exist")
    if len(wife) == 0:
        raise Exception("Wife does not exist")
    if wife[0]["gender"] != "F" or husband[0]["gender"] != "M":
        raise Exception(f"LGBTQWERTY detected!")

    return "OK"
    $$;

create or replace function check_tree_loops() returns TRIGGER language plpython3u AS $$
    new = TD["new"]
    plan = plpy.prepare("SELECT * FROM people_hierarchy WHERE child_id = $1 and parent_id = $2;", ['int', 'int'])
    if len(plpy.execute(plan, [new['parent_id'], new['child_id']])) > 0:
        raise Exception('Hierarchy loop detected')
    plan = plpy.prepare("SELECT * FROM people WHERE id = $1;", ['int'])
    new_parent = plpy.execute(plan, [new['parent_id']])[0]

    plan = plpy.prepare("SELECT * FROM parent_children pc INNER JOIN people ppl ON ppl.id = pc.parent_id AND ppl.gender = $2 WHERE pc.child_id = $1;", ['int', 'public.gender'])
    res = plpy.execute(plan, [new['child_id'], new_parent['gender']])
    if len(res) > 0:
        raise Exception(f'Child already has parent of this gender! {new} {res} {new_parent}')

    return "OK"
    $$;



create schema xml


    create table people
    (
        id     int unique not null primary key,
        gender gender      not null,
        name   text        not null
    )

    create table pairs
    (
        husband_id int unique not null references people,
        wife_id int unique not null references people,
        CHECK ( husband_id != wife_id )
    )

    create table parent_children
    (
        parent_id int not null references people,
        child_id  int not null references people,
        check ( parent_id != child_id ),
        PRIMARY KEY (parent_id, child_id)
    )

    create trigger faggot_protector
        BEFORE insert or update on pairs
        for each row execute function check_marriage()

    create trigger family_values_enforcer
        BEFORE insert or update on parent_children
        for each row execute function check_tree_loops()

    create view people_hierarchy AS -- https://stackoverflow.com/questions/60534878/prevent-loops-in-many-to-many-self-referencing-relationship-postgres
    WITH recursive children AS (
        SELECT
            child_id,
            parent_id
        FROM parent_children
        UNION SELECT
            children.child_id,
            parents.parent_id
        FROM parent_children as parents
        INNER JOIN children
            ON children.parent_id = parents.child_id

    ) SELECT * FROM children

    create  view siblings AS
        SELECT distinct
            first_people.child_id as person,
            siblings.child_id as sibling
        FROM parent_children first_people
        INNER JOIN parent_children siblings ON first_people.parent_id = siblings.parent_id AND first_people.child_id != siblings.child_id;

--     create table

--     create materialized view siblings as
--         select distinct person.id as person, siblings.child as sibling from people person
--         inner join parent_children parent on person.id = parent.child
--         inner join parent_children siblings on parent.parent = siblings.parent and siblings.child != person.id
SET search_path TO xml;
insert into people (id, gender, name) values
    (1, 'M', 'M 1'),
    (2, 'F', 'F 1'),
    (3, 'F', 'F 2'),
    (4, 'F', 'F 3'),
    (5, 'M', 'M 2'),
    (6, 'F', 'F 4'),
    (7, 'M', 'M 3'),
    (8, 'M', 'M 4');

INSERT INTO pairs (HUSBAND_ID, WIFE_ID) VALUES
    (1, 2),
    (5, 6);

INSERT INTO parent_children (PARENT_ID, CHILD_ID) VALUES
    (1,3),
    (1,4),
    (1,5),
    (2,3),
    (2,4),
    (2,5),
    (5,7),
    (5,8),
    (6,7),
    (6,8);

-- insert into pairs (husband_id, wife_id) VALUES (1, 2); -- polygamy
-- insert into pairs (husband_id, wife_id) VALUES (3, 4); -- faggots
-- insert into parent_children (parent_id, child_id) VALUES (1, 4); -- duplicate parent
-- insert into parent_children (parent_id, child_id) VALUES (7, 1); -- loop
