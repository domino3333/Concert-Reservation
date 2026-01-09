package mvc.task;

import mvc.model.Customer;
import mvc.service.ReservationService;

public class CustomerTask implements Runnable {
    private final Customer customer;
    ReservationService rs = ReservationService.getInstance();


    public CustomerTask(Customer c){
        this.customer = c;
    }
    @Override
    public void run() {
        long ran = (long)(Math.random()*500);
        try {
            Thread.sleep(ran);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rs.reserveSeat(randomSeatMaker(),customer.getName());
    }

    private String randomSeatMaker() {

        char[] charArr = new char[] {'A','B','C','D','E'};

        int randInt = (int)(Math.random()*(5-1+1)+1);
        int randCharNumber = (int)(Math.random()*(4 +1)+0);

        char temp = charArr[randCharNumber];

        return String.valueOf(temp) + randInt;
    }
}
