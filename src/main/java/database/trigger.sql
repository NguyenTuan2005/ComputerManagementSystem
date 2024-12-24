CREATE OR REPLACE FUNCTION fn_update_stock()
    RETURNS TRIGGER AS $$
BEGIN
    -- Kiểm tra nếu trạng thái mới là 'Order processed for shipping'
    IF NEW.status = 'Cancel' THEN
        UPDATE public.product as pr
        SET quantity = pr.quantity + od.quantity
        FROM order_detail as od
        WHERE od.product_id = pr.id
          AND od.order_id = NEW.id;
        RAISE NOTICE 'cancel order';
    end if;
    IF NEW.status = 'Order processed for shipping' THEN
        UPDATE public.product as pr
        SET quantity = pr.quantity - od.quantity
        FROM order_detail as od
        WHERE od.product_id = pr.id
          AND od.order_id = NEW.id;
        RAISE NOTICE 'Order processed for shipping';
    End if;

    -- Trả về bản ghi mới
    RETURN NEW;
END;
-- $$ LANGUAGE plpgsql;

-- Trigger
CREATE TRIGGER TR_Order_update_stock
    AFTER UPDATE OF status ON public.order
    FOR EACH ROW
    WHEN (OLD.status IS DISTINCT FROM NEW.status) -- Chỉ kích hoạt nếu status thay đổi
EXECUTE FUNCTION fn_update_stock();