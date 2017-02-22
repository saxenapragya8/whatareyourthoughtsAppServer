CREATE or REPLACE function add_user(in usr_name character varying,
                                    in email_id character varying, 
                                    in usr_id character varying, 
                                    out status boolean,
                                   out wayt_usr_id integer) AS $$
DECLARE wayt_user_id integer;
BEGIN
  status := false;
  select id into wayt_usr_id from users where email=$2;
  
  if( wayt_usr_id is null or wayt_usr_id <= 0) then
  	insert into users(email, remember_created_at, name, created_at, updated_at) values($2, CURRENT_TIMESTAMP, $1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
    select id into wayt_usr_id from users where email=$2;
  end if;
  status := true;
  
END;
$$ LANGUAGE plpgsql;

commit