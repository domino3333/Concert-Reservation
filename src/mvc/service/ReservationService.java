package mvc.service;

import mvc.common.JDBCTemplate;
import mvc.model.*;
import mvc.repository.MemberDao;
import mvc.repository.ReservationDao;
import mvc.repository.SeatDao;
import mvc.task.CustomerTask;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class ReservationService {

    public static final int SEAT_ROW = 5;
    public static final int SEAT_COL = 5;
    private final static ReservationService rs = new ReservationService();
    public final int CUSTOMER_COUNT = 10;
    public final String[] CUSTOMER_NAME = {"김길동", "성시경", "브라이언", "김득춘", "저길동", "김점순", "장그래", "폴 킴", "최길숙", "전상훈",
            "김할머니", "김정김", "동글이", "손철수", "김할아버지", "짱구", "강주연", "최이슬", "수지", "존박", "박명수", "정준하"};
    private boolean isReserving = false;

    public static ReservationService getInstance() {
        return rs;
    }

    /**
     * 손님과 내가 자리예매할 때 진입하는 동기화 함수
     *
     * @param seatNumber:   원하는 자리
     * @param customerName: 예매자 이름
     */
    public synchronized void reserveSeat(String seatNumber, String customerName) {

        while (isReserving) {
            System.out.println("다른 사람이 예매 중입니다. 잠시만 기다려주세요.");
            while (isReserving) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("wait 인터럽트 예외 발생");
                }
            }
        }
        isReserving = true;
        long ran = (long) (Math.random() * (2000 - 1650 + 1) + 1650);
        try {
            System.out.println(customerName + "님이" + seatNumber + " 예약중..");
            Thread.sleep(ran);
            //체크해야됨
            int rowCount = new SeatDao().updateAvailable(seatNumber, customerName);
            if (rowCount > 0) {
                System.out.println(customerName + "님" + seatNumber + " 예매 완료!");
            } else {
                //예약 불가능, 그냥 실패 때림
                System.out.println("이미 예약된 좌석입니다." + customerName + "님 예약 실패");
            }

            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            isReserving = false;
            notifyAll();
        }
    }


/*    public void initSeats() {
        char rowChar = 'A';
        for (int i = 0; i < SEAT_ROW; i++) {
            for (int j = 0; j < SEAT_COL; j++) {
                String temp = (rowChar) + String.valueOf(j + 1);
                seatArr[i][j] = new SeatItem(temp, true);
            }
            rowChar++;
        }
    }*/

    /**
     * 손님 초기화(랜덤자리배정,
     *
     * @return 손님 배열
     */
    public Customer[] initCustomer() {
        // 회원 생성
        Customer[] customerArr = new Customer[CUSTOMER_COUNT];

        for (int i = 0; i < customerArr.length; i++) {
            int nameRandomNumber = (int) (Math.random() * (CUSTOMER_NAME.length - 1 + 1) + 0); // 0~까지 랜덤 숫자 생성
            int ageRandomNumber = (int) (Math.random() * (80 - 4 + 1) + 4); // 4~80까지 랜덤 숫자 생성

            String randomName = CUSTOMER_NAME[nameRandomNumber];

            customerArr[i] = new Customer();
            customerArr[i].setName(randomName);
            customerArr[i].setAge(ageRandomNumber);
        }
        return customerArr;
    }

    // 예매 시 나이 제한 체크
    public int checkAgeBeforeReserve(Concert concert) {
        //최근 가입 회원 나이 가져오기
        int memberAge = new MemberDao().returnLatestRegisterMember().getAge();

        if (concert instanceof BalladConcert temp) {
            if (temp.getAccessAge() > memberAge) {
                //발라드 나이제한 예매 불가
                return 1;
            } else return 2; // 발라드 예매가능
        } else {
            DanceConcert temp = (DanceConcert) concert;
            if (temp.getAccessAge() > memberAge) {
                //댄스 나이제한 예매 불가
                return 3;
            } else return 4; // 댄스 예매 가능
        }
    }

    public List<Thread> customerReservationThreadStart() {
        List<Thread> customerThread = new ArrayList<>();
        for (Customer c : initCustomer()) {
            Thread t = new Thread(new CustomerTask(c));
            t.start();
            customerThread.add(t);
        }
        return customerThread;
    }

    public Thread myReservationThreadStart(String mySeat, String myName) {
        Thread myThread = new Thread(() -> {
            long ran = (long) (Math.random() * 499);
            try {
                Thread.sleep(ran);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reserveSeat(mySeat, myName);
        });
        myThread.start();

        return myThread;

    }

    public List<Character> selectSeatIsAvailable() {
        return new SeatDao().isAvailableSeat();
    }

    public int insertMyReservationInfo(String mySeat, String myName, Concert selectedConcert) {

        Connection con = JDBCTemplate.getConnection();


        //todo 내 정보 안에 concert객체 하나 갖고있어야 함
        // 그걸 여기서 new로 만들어 넘겨
        Member m = new Member();
        m.setName(myName);
        m.setConcert(selectedConcert);
        m.setMySeat(mySeat);

        int rowCount = new ReservationDao().insertMyReservationInfo(con, m);
        if (rowCount > 0) {
            JDBCTemplate.commit(con);
        } else {
            JDBCTemplate.rollback(con);

        }

        JDBCTemplate.close(con);

        return rowCount;


    }
}


