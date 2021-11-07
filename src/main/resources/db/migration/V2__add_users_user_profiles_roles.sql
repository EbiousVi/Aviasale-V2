create TABLE users (
  user_id BIGINT GENERATED ALWAYS AS IDENTITY,
  user_email VARCHAR(64) NOT NULL UNIQUE,
  user_password VARCHAR(255) NOT NULL,
  user_refresh_token VARCHAR(255),
  CONSTRAINT pk_users PRIMARY KEY (user_id)
);

create TABLE user_profiles (
  user_id BIGINT GENERATED ALWAYS AS IDENTITY,
  user_full_name VARCHAR(64) NOT NULL,
  user_passport VARCHAR(16) NOT NULL,
  user_contact_data JSONB NOT NULL,
  CONSTRAINT pk_user_profiles PRIMARY KEY (user_id)
);

create TABLE roles (
  role_id BIGINT GENERATED ALWAYS AS IDENTITY,
  role_name VARCHAR(64) NOT NULL UNIQUE,
  CONSTRAINT pk_roles PRIMARY KEY (role_id)
);

create TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL
);

alter table user_profiles add CONSTRAINT FK_USER_PROFILES_ON_USERS FOREIGN KEY (user_id) REFERENCES users (user_id) DEFERRABLE INITIALLY DEFERRED;

alter table users_roles add CONSTRAINT FK_USERS_ON_ROLES FOREIGN KEY (user_id) REFERENCES users (user_id);
alter table users_roles add CONSTRAINT FK_ROLES_ON_USERS FOREIGN KEY (role_id) REFERENCES roles (role_id);
