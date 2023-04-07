create table ingredient
(
    id           bigserial
        primary key,
    created_date timestamp with time zone not null,
    updated_date timestamp with time zone,
    name         varchar(255)             not null
        constraint uk_ingredient_name
            unique
);

create table recipe
(
    id              bigserial
        primary key,
    created_date    timestamp with time zone not null,
    updated_date    timestamp with time zone,
    instruction     varchar(255)             not null,
    name            varchar(255)             not null,
    number_servings integer                  not null,
    prep_time       varchar(255)             not null,
    type            varchar(255)             not null
);


create table recipe_ingredient
(
    recipe_id     bigint not null
        constraint fk_ingredient_recipe_recipe_id
            references recipe,
    ingredient_id bigint not null
        constraint fk_ingredient_recipe_ingredient_id
            references ingredient,
    primary key (recipe_id, ingredient_id)
);




