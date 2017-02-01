CREATE OR REPLACE FUNCTION add_new_conversation(usr_id integer, subject character varying(255), 
                                            art_link character varying(255), slug character varying(255),
                                            recipient_ids character varying(255)[], out status boolean,
                                            comment text) AS $$
	declare new_conv_id integer;
    declare new_conv_participation_id integer;
    declare i integer;
	declare arrLen integer;
    BEGIN
    	status := false;
		SELECT array_length(recipient_ids , 1 ) into arrLen;
        select nextval('conversations_id_seq') into new_conv_id;
        insert into conversations(id, user_id, subject, source_link, slug ,created_at, updated_at, draft, recipient_ids) values(new_conv_id, $1, $2, $3, $4, now()::date, now()::date, true, recipient_ids);
        select nextval('participations_id_seq') into new_conv_participation_id;
        insert into participations(id, user_id, conversation_id, read, important,others_count, created_at, updated_at, mute) values(new_conv_participation_id, usr_id, new_conv_id, false, false, 0, now()::date, now()::date, false);
        insert into comments(participation_id, conversation_id, content, created_at, updated_at) values(new_conv_participation_id, new_conv_id, comment, now()::date, now()::date);
		if(arrLen > 0) then
        	FOREACH i IN ARRAY recipient_ids
            LOOP 
               insert into participations(user_id, conversation_id, read, important,others_count, created_at, updated_at, mute) values(i, new_conv_id, false, false, 0, now()::date, now()::date, false);
            END LOOP;
		end if;
        
        status := true;
    END;
    $$ LANGUAGE plpgsql;

   commit