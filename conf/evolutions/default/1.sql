# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "BOOK" ("ID" INTEGER NOT NULL PRIMARY KEY,"TITLE" VARCHAR NOT NULL,"AUTHOR" VARCHAR NOT NULL,"RELEASE_DATE" INTEGER NOT NULL,"KEYWORDS" VARCHAR NOT NULL,"COVER_IMAGE" VARCHAR NOT NULL);

# --- !Downs

drop table "BOOK";

