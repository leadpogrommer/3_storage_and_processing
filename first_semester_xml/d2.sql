drop schema if exists xml cascade;
drop type if exists gender cascade;

CREATE type gender AS ENUM ('F', 'M');

create schema xml

    create table people
    (
        id     text unique not null primary key,
        gender gender      not null,
        name   text        not null,
        spouse text references people
    )

    create table parent_children
    (
        parent text not null references people,
        child  text not null references people
    )

    create table siblings
    (
        person  text not null references people,
        sibling text not null references people
    );