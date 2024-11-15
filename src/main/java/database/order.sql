select
    o.customer_id,
    o.orderdata as "Customer",
    o.ship_address,
    o.status,
    m.fullname as "Saler",
    m.id as "ma Nhan vien",
    od.unit_price,
    od.quantity,
    od.product_id,
    p.name,
    p.quality,
    p.genre,
    p.brand,
    p.operating_system,
    p.cpu,
    p.memory,
    p.ram,
    p.made_in,
    p.disk,
    p.weight,
    p.monitor,
    p.card


from  public.order as o
          inner join manager as m
                     on m.id = o.manager_id

          inner join order_detail as od
                     on od.order_id = o.id

          inner join product as p
                     on od.product_id = p.id

where o.customer_id = 1


-- view order

CREATE VIEW customer_order_view AS
SELECT
    o.customer_id AS customer_id,
    o.orderdata AS order_date,
    o.ship_address AS ship_address,
    o.status AS status_item,
    m.fullname AS saler,
    m.id AS saler_id,
    od.unit_price AS unit_price,
    od.quantity AS quantity,
    od.product_id AS product_id,
    p.name AS product_name,
    p.genre AS product_genre,
    p.brand AS product_brand,
    p.operating_system AS operating_system,
    p.cpu AS cpu,
    p.memory AS memory,
    p.ram AS ram,
    p.made_in AS made_in,
    p.disk AS disk,
    p.weight AS weight,
    p.monitor AS monitor,
    p.card AS card
FROM public.order AS o
         INNER JOIN manager AS m ON m.id = o.manager_id
         INNER JOIN order_detail AS od ON od.order_id = o.id
         INNER JOIN product AS p ON od.product_id = p.id
