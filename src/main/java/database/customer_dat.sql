select * from customer;
-- update customer set avata_img ='src/main/java/img/james.JPG';
ALTER TABLE customer
    ADD COLUMN number_of_combs_purchased INT DEFAULT 0;

insert into customer (fullname , email,address,password,avata_img)
values('Nguyen Thi Ngoc Huyen','23130075@st.hcmuaf.edu.vn','tien giang chau thanh duong diem',
       '$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO','src/main/java/img/cus_huyen.jpg');

insert into customer (fullname , email,address,password,avata_img)
values('Nguyen Tran Kim Trang','abc@st.hcmuaf.edu.vn','tphmc-q8-abc...',
       '$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO','src/main/java/img/trang.jpg');


insert into customer (fullname , email,address,password,avata_img)
values('Le Tan Phuoc','abce@st.hcmuaf.edu.vn','Tiền Giang châu thành, bình trưng,123',
       '$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO','src/main/java/img/anh7.png');

insert into customer (fullname , email,address,password,avata_img)
values('Nguyen Van Chay','abceww@st.hcmuaf.edu.vn','Tiền Giang châu thành,nhị quý,234',
       '$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO','src/main/java/img/chay.png');

insert into customer (fullname , email,address,password,avata_img)
values('Nguyen Van Hieu','abcewsadw@st.hcmuaf.edu.vn','tpHCM, thu duc nlu duong so6',
       '$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO','src/main/java/img/hieu2.png');

insert into customer (fullname , email,address,password,avata_img)
values('Nguyen Thi kim Thanh','abaac@st.hcmuaf.edu.vn','tphmc-q9-abc...',
       '$2y$10$iT4bC2hnmfNmouE1KSOCKubEW3MJJWi0mQP50L89K2sLK8ztPCjXO','src/main/java/img/dom.jpg');





