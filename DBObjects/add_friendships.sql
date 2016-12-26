CREATE or REPLACE function add_friendship (in user_id integer,in mail_id char(255), out is_friend_user integer,out is_already_friend integer) AS $$
DECLARE friend_id integer;
BEGIN
  select id into friend_id from users where trim(email)=trim($2);
  select count(*) into is_already_friend from friendships where user_id = $1 and friend_id = friend_id;
  if friend_id > 0 and is_already_friend > 0 then
  	insert into friendships(user_id, friend_id) values($1, friend_id);
  	is_friend_user := 1;
  else
  	is_friend_user := 0;
  end if;
END;
$$ LANGUAGE plpgsql;