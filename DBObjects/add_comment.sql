CREATE or REPLACE function add_comment(in conv_id integer,in usr_id integer, in comment text, out status boolean) AS $$
DECLARE part_id integer;
BEGIN
  status := false;
  select id into part_id from participations where user_id=$2 and conversation_id=$1;
  insert into comments(participation_id, conversation_id, content, created_at, updated_at) values(part_id, conv_id, comment, now()::date, now()::date);
  status := true;
END;
$$ LANGUAGE plpgsql;

commit