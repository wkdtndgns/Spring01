package Service;

import Dao.Order;
import Dao.OrderDao;
import Dao.Production;
import Dao.ProductionDao;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class OrderService {

  private final OrderDao orderDao;
  private final ProductionDao productionDao;

  public OrderService() {
    this.productionDao = new ProductionDao();
    this.orderDao = new OrderDao();
  }

  public List<Order> getList() {
    return orderDao.findAll();
  }

  public void updateOrderStatus(List<Integer> orderId, int newStatus) {
    orderDao.updateStatus(orderId, newStatus);
  }

  @Transactional
  public void registerOrder(Order order) {
    LocalDateTime localDateTimeNow = LocalDateTime.now();
    Date startDate = Date.valueOf(localDateTimeNow.toLocalDate());
    Date expectedDeliveryDate = Date.valueOf(localDateTimeNow.plusDays(7).toLocalDate()); // Set expected delivery date 7 days from now
    Date expectedDDate = Date.valueOf(localDateTimeNow.plusDays(3).toLocalDate()); // Set expected delivery date 7 days from now

    // Create an Order object for testing
//    Order order = new Order(
//        1,                   // companyId
//        1,                   // factoryId
//        1,                   // materialId
//        400,                   // qty
//        500.0f,                // purchasePrice
//        startDate,             // orderDate
//        expectedDeliveryDate,  // expectedDeliveryDate
//        10                     // status
//    );
    int id = orderDao.insertOrder(order);

    Production production = new Production(id, order.getFactoryId(), order.getQty(), startDate, expectedDDate, 10);
    productionDao.insertProduction(production);
  }
}
