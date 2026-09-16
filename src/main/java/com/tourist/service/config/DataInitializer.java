package com.tourist.service.config;

import com.tourist.service.domain.*;
import com.tourist.service.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 启动时初始化演示账号与示例数据。
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final HotelRepository hotelRepository;
    private final RoomInfoRepository roomInfoRepository;
    private final ScenicSpotRepository spotRepository;
    private final TouristRouteRepository routeRepository;
    private final CateringPlaceRepository cateringRepository;
    private final PerformanceGroupRepository performanceRepository;
    private final WeatherInfoRepository weatherRepository;
    private final RoadInfoRepository roadRepository;
    private final EmergencyInfoRepository emergencyRepository;
    private final ComplaintRepository complaintRepository;
    private final ComplaintHandlingRepository handlingRepository;
    private final ComplaintFeedbackRepository feedbackRepository;
    private final ConsultationRepository consultationRepository;
    private final TransportRepository transportRepository;
    private final BookingRepository bookingRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }
        seedUsers();
        seedHotels();
        seedSpotsAndRoutes();
        seedCatering();
        seedWeatherAndRoad();
        seedTransport();
        seedEmergency();
        seedComplaints();
        seedConsultation();
        seedBooking();
    }

    private void seedUsers() {
        saveUser("tourist01", "张三", Role.TOURIST, "13800000001");
        saveUser("tourist02", "李四", Role.TOURIST, "13800000002");
        saveUser("admin01", "平台管理员", Role.PLATFORM_ADMIN, "13900000001");
        saveUser("approver01", "信息审批员", Role.APPROVER, "13900000002");
        saveUser("handler01", "投诉处理员", Role.COMPLAINT_HANDLER, "13800000003");
        saveUser("hotel01", "酒店管理员", Role.HOTEL_MANAGER, "13800000004");
    }

    private User saveUser(String username, String name, Role role, String phone) {
        return userRepository.save(User.builder()
                .username(username)
                .password(encoder.encode("123456"))
                .name(name)
                .role(role)
                .phone(phone)
                .build());
    }

    private void seedHotels() {
        Hotel star = hotelRepository.save(Hotel.builder()
                .name("云顶国际酒店")
                .hotelType(HotelType.STAR)
                .starLevel(5)
                .address("景区南门大道 1 号")
                .phone("0571-88888888")
                .description("五星级豪华酒店，拥有景观湖景房与高端餐饮，步行可达游客中心。")
                .image("hotel-1.jpg")
                .build());
        hotelRepository.save(Hotel.builder()
                .name("翠湖度假大酒店")
                .hotelType(HotelType.STAR)
                .starLevel(4)
                .address("环湖路 12 号")
                .phone("0571-77777777")
                .description("四星级度假酒店，配备亲子乐园与温泉，适合家庭出游。")
                .image("hotel-2.jpg")
                .build());

        Hotel nonStar = hotelRepository.save(Hotel.builder()
                .name("山水人家宾馆")
                .hotelType(HotelType.NON_STAR)
                .address("游客中心东侧 8 号")
                .phone("0571-66666666")
                .description("经济型宾馆，干净整洁，交通便利。")
                .image("hotel-3.jpg")
                .build());

        Hotel rural = hotelRepository.save(Hotel.builder()
                .name("南山民宿")
                .hotelType(HotelType.RURAL)
                .address("南山村 27 号")
                .phone("0571-55555555")
                .description("田园风格民宿，提供农家菜与山景露台。")
                .image("hotel-4.jpg")
                .build());

        User hotelUser = userRepository.findByUsername("hotel01").orElseThrow();
        roomInfoRepository.save(RoomInfo.builder()
                .hotel(star).roomType("湖景大床房").price(new BigDecimal("888"))
                .totalRooms(50).availableRooms(18).updatedBy(hotelUser).updatedAt(LocalDateTime.now())
                .build());
        roomInfoRepository.save(RoomInfo.builder()
                .hotel(star).roomType("标准双床房").price(new BigDecimal("588"))
                .totalRooms(80).availableRooms(36).updatedBy(hotelUser).updatedAt(LocalDateTime.now())
                .build());
        roomInfoRepository.save(RoomInfo.builder()
                .hotel(nonStar).roomType("单人间").price(new BigDecimal("188"))
                .totalRooms(30).availableRooms(9).updatedBy(hotelUser).updatedAt(LocalDateTime.now())
                .build());
        roomInfoRepository.save(RoomInfo.builder()
                .hotel(rural).roomType("山景家庭房").price(new BigDecimal("328"))
                .totalRooms(12).availableRooms(4).updatedBy(hotelUser).updatedAt(LocalDateTime.now())
                .build());
    }

    private void seedSpotsAndRoutes() {
        spotRepository.save(ScenicSpot.builder().name("云顶日出").category("自然景观")
                .location("云顶山主峰").openTime("05:00-09:00").ticketPrice(new BigDecimal("80"))
                .description("著名的云海日出观景点，秋季景色最佳。").image("spot-1.jpg").build());
        spotRepository.save(ScenicSpot.builder().name("古刹禅寺").category("人文古迹")
                .location("景区中部").openTime("08:00-17:00").ticketPrice(new BigDecimal("40"))
                .description("千年古寺，环境幽静，殿宇保存完好。").image("spot-2.jpg").build());
        spotRepository.save(ScenicSpot.builder().name("玻璃栈道").category("体验项目")
                .location("断崖山").openTime("09:00-18:00").ticketPrice(new BigDecimal("120"))
                .description("全长 200 米的高空玻璃栈道，惊险刺激。").image("spot-3.jpg").build());

        routeRepository.save(TouristRoute.builder().name("经典一日游")
                .spots("云顶日出,古刹禅寺,玻璃栈道").duration("约8小时").price(new BigDecimal("180"))
                .description("覆盖景区核心景点的经典一日游线路。").build());
        routeRepository.save(TouristRoute.builder().name("亲子两日游")
                .spots("古刹禅寺,翠湖度假大酒店,玻璃栈道").duration("2天1夜").price(new BigDecimal("360"))
                .description("适合亲子家庭，包含住宿与门票。").build());
        routeRepository.save(TouristRoute.builder().name("深度文化游")
                .spots("古刹禅寺,云顶日出").duration("约6小时").price(new BigDecimal("150"))
                .description("人文与自然结合的深度游览线路。").build());
        routeRepository.save(TouristRoute.builder().name("环湖休闲游")
                .spots("翠湖景区,玻璃栈道").duration("约5小时").price(new BigDecimal("120"))
                .description("沿翠湖休闲观光，适合慢节奏游览。").build());
    }

    private void seedCatering() {
        cateringRepository.save(CateringPlace.builder().name("山顶餐厅").type(CateringType.DINING)
                .address("云顶山观景台旁").phone("0571-11111111").avgPrice(new BigDecimal("120"))
                .description("本地特色菜与山野时蔬，观景位可俯瞰云海。").image("food-1.jpg").build());
        cateringRepository.save(CateringPlace.builder().name("景区夜市").type(CateringType.ENTERTAINMENT)
                .address("游客中心广场").phone("0571-22222222").avgPrice(new BigDecimal("60"))
                .description("夜间美食街与娱乐表演，适合晚间休闲。").image("food-2.jpg").build());

        performanceRepository.save(PerformanceGroup.builder().name("江南丝竹乐团").category("民乐演出")
                .address("古戏台").contact("0571-33333333")
                .description("以江南丝竹为代表的传统文化演出团体。").build());
        performanceRepository.save(PerformanceGroup.builder().name("星光歌舞团").category("歌舞演出")
                .address("大剧场").contact("0571-44444444")
                .description("大型歌舞综艺演出，节假日定期上演。").build());
    }

    private void seedWeatherAndRoad() {
        weatherRepository.save(WeatherInfo.builder().area("景区主城区").forecastDate(LocalDate.now())
                .weather("多云转晴").temperature("18~26℃").humidity("55%").wind("东风2级")
                .suggestion("天气舒适，适合户外游览，早晚注意添衣。").build());
        weatherRepository.save(WeatherInfo.builder().area("云顶山").forecastDate(LocalDate.now())
                .weather("小雨").temperature("12~17℃").humidity("78%").wind("北风3级")
                .suggestion("有降水，登山请携带雨具并注意湿滑路段。").build());

        roadRepository.save(RoadInfo.builder().roadName("景区大道").section("南门至游客中心")
                .status(RoadStatus.SMOOTH).description("路面通畅，车流量适中。").updatedAt(LocalDateTime.now()).build());
        roadRepository.save(RoadInfo.builder().roadName("环湖路").section("东段")
                .status(RoadStatus.SLOW).description("因停车场满员，局部缓行。").updatedAt(LocalDateTime.now()).build());
        roadRepository.save(RoadInfo.builder().roadName("盘山公路").section("云顶山路段")
                .status(RoadStatus.CONTROLLED).description("因降雨实行临时交通管制，请绕行。").updatedAt(LocalDateTime.now()).build());
    }

    private void seedTransport() {
        transportRepository.save(Transport.builder().name("景区观光电瓶车")
                .type("观光车").route("游客中心—云顶山脚").schedule("08:00-18:00")
                .frequency("每15分钟一班").price(new BigDecimal("20"))
                .description("景区内循环运营的观光电瓶车，沿途停靠主要景点。").build());
        transportRepository.save(Transport.builder().name("环湖旅游大巴")
                .type("旅游大巴").route("南门—环湖—东门").schedule("09:00-17:00")
                .frequency("每30分钟一班").price(new BigDecimal("30"))
                .description("环湖旅游专线大巴，连接主要酒店与景点。").build());
        transportRepository.save(Transport.builder().name("翠湖游船")
                .type("游船").route("湖心码头—望湖亭").schedule("10:00-16:00")
                .frequency("每40分钟一班").price(new BigDecimal("60"))
                .description("翠湖景区观光游船，可环湖游览。").build());
    }

    private void seedEmergency() {
        User publisher = userRepository.findByUsername("admin01").orElseThrow();
        User approver = userRepository.findByUsername("approver01").orElseThrow();
        emergencyRepository.save(EmergencyInfo.builder()
                .title("关于景区南门停车场高峰时段预约的提示")
                .content("五一高峰期间，景区南门停车场实行分时预约，请游客提前在官方小程序预约车位。")
                .category("交通提示").validFrom(LocalDate.now()).validTo(LocalDate.now().plusDays(10))
                .status(EmergencyStatus.PUBLISHED).publisher(publisher).approver(approver)
                .approveRemark("核实无误，同意发布。").publishedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now().minusDays(1)).build());
        emergencyRepository.save(EmergencyInfo.builder()
                .title("雷雨天气景区索道停运通知")
                .content("因雷雨天气，云顶山索道临时停运，恢复时间另行通知，请合理安排行程。")
                .category("安全提示").validFrom(LocalDate.now()).validTo(LocalDate.now().plusDays(3))
                .status(EmergencyStatus.PENDING_APPROVAL).publisher(publisher)
                .createdAt(LocalDateTime.now().minusHours(3)).build());
    }

    private void seedComplaints() {
        User tourist = userRepository.findByUsername("tourist01").orElseThrow();
        User admin = userRepository.findByUsername("admin01").orElseThrow();
        User handler = userRepository.findByUsername("handler01").orElseThrow();

        complaintRepository.save(Complaint.builder()
                .tourist(tourist).title("景区烧烤摊占道经营")
                .content("周末在景区夜市发现烧烤摊位占用人行道，影响通行且有油烟扰民。")
                .status(ComplaintStatus.PENDING_APPROVAL)
                .createdAt(LocalDateTime.now().minusHours(2)).updatedAt(LocalDateTime.now().minusHours(2))
                .build());

        Complaint handling = complaintRepository.save(Complaint.builder()
                .tourist(tourist).title("观光车候车时间过长")
                .content("下午两点在观光车站候车超过 40 分钟，缺少工作人员引导。")
                .status(ComplaintStatus.IN_HANDLING).approver(admin).handler(handler)
                .approveRemark("属实，转处理人员核实。")
                .createdAt(LocalDateTime.now().minusDays(1)).updatedAt(LocalDateTime.now().minusHours(5))
                .build());

        Complaint handled = complaintRepository.save(Complaint.builder()
                .tourist(tourist).title("公厕卫生环境差")
                .content("景区东侧公厕异味较重，地面湿滑，希望改进。")
                .status(ComplaintStatus.HANDLED).approver(admin).handler(handler)
                .approveRemark("同意处理。")
                .createdAt(LocalDateTime.now().minusDays(3)).updatedAt(LocalDateTime.now().minusDays(1))
                .build());
        handlingRepository.save(ComplaintHandling.builder()
                .complaint(handled).handler(handler)
                .handleOpinion("已现场核查，保洁频次不足。")
                .handleResult("已增加保洁频次，增设防滑垫并安排专人值守。")
                .handledAt(LocalDateTime.now().minusDays(1)).build());

        Complaint closed = complaintRepository.save(Complaint.builder()
                .tourist(tourist).title("志愿者服务态度问题")
                .content("咨询台志愿者解答问题时态度较冷淡。")
                .status(ComplaintStatus.CLOSED).approver(admin).handler(handler)
                .approveRemark("属实。")
                .createdAt(LocalDateTime.now().minusDays(6)).updatedAt(LocalDateTime.now().minusDays(2))
                .build());
        handlingRepository.save(ComplaintHandling.builder()
                .complaint(closed).handler(handler)
                .handleOpinion("已与当事志愿者沟通。")
                .handleResult("已批评教育并服务礼仪培训。")
                .handledAt(LocalDateTime.now().minusDays(3)).build());
        feedbackRepository.save(ComplaintFeedback.builder()
                .complaint(closed).rating(4).feedback("处理及时，服务有所改进。")
                .ratedAt(LocalDateTime.now().minusDays(2)).build());
    }

    private void seedConsultation() {
        User tourist = userRepository.findByUsername("tourist01").orElseThrow();
        User admin = userRepository.findByUsername("admin01").orElseThrow();
        consultationRepository.save(Consultation.builder()
                .tourist(tourist).title("景区门票优惠政策")
                .content("请问学生证购票有优惠吗？需要携带什么证件？")
                .status(ConsultationStatus.PENDING)
                .createdAt(LocalDateTime.now().minusHours(1)).build());
        consultationRepository.save(Consultation.builder()
                .tourist(tourist).title("宠物能否带入景区")
                .content("我想带宠物逛景区，请问是否有规定？")
                .status(ConsultationStatus.ANSWERED).answerer(admin)
                .answer("抱歉，为保障游客安全，景区暂不支持携带宠物入园。")
                .createdAt(LocalDateTime.now().minusDays(1)).answeredAt(LocalDateTime.now().minusHours(20)).build());
    }
    private void seedBooking() {
        User tourist = userRepository.findByUsername("tourist01").orElseThrow();
        RoomInfo room = roomInfoRepository.findAll().get(0);
        bookingRepository.save(Booking.builder()
                .room(room).tourist(tourist).guestName("张三").phone("13800000001")
                .checkIn(LocalDate.now().plusDays(2)).checkOut(LocalDate.now().plusDays(4))
                .status(BookingStatus.PENDING)
                .createdAt(LocalDateTime.now()).build());
        bookingRepository.save(Booking.builder()
                .room(room).tourist(tourist).guestName("李四").phone("13800000002")
                .checkIn(LocalDate.now().plusDays(3)).checkOut(LocalDate.now().plusDays(5))
                .status(BookingStatus.CONFIRMED)
                .createdAt(LocalDateTime.now().minusDays(1)).build());
    }
}