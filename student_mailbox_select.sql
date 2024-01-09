SELECT 
    ioio.mailbox_student.id_box_stud, 
    offers.id_offer, 
    users.login AS student_login,
    mailbox_student.mess_type, 
    users_login.login AS employee_login, 
    offers.title
FROM 
    offers
INNER JOIN 
    mailbox_student ON offers.id_offer = mailbox_student.id_offer
INNER JOIN 
    users ON mailbox_student.id_stud = users.id_user
LEFT JOIN 
    users AS users_login ON offers.id_empl = users_login.id_user
WHERE 
    users.id_user IN (0);
