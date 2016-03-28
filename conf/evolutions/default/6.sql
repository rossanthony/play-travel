# --- !Ups
create table `booking` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `userId` varchar(254) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  `created` timestamp not null default CURRENT_TIMESTAMP,
  `updated` timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

# --- !Downs
drop table `booking`;
