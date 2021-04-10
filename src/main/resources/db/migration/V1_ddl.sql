create table category
(
    id                 uuid         not null,
    code               varchar(255) not null,
    description        text,
    "name"               varchar(500),
    sequence_number       integer,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    constraint category_pkey
        primary key (id),
    constraint category_code_constraint
        unique (code)
);

CREATE TABLE product (
  id UUID NOT NULL,
  part_number VARCHAR(64) NOT NULL,
  short_desc VARCHAR(64) NOT NULL,
  long_desc VARCHAR(200) NOT NULL,
  category_id UUID,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (part_number),
  PRIMARY KEY (id),
  constraint product_category_ref
        foreign key (category_id) references category
);
