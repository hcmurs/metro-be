package com.example.stationservice.config;


import com.example.stationservice.dto.RoutesRequest;
import com.example.stationservice.dto.RoutesResponse;
import com.example.stationservice.dto.SchedulesRequest;
import com.example.stationservice.model.Routes;
import com.example.stationservice.model.Schedules;
import com.example.stationservice.model.Stations;
import com.example.stationservice.repository.RoutesRepository;
import com.example.stationservice.repository.StationsRepository;
import com.example.stationservice.service.RoutesService;
import com.example.stationservice.service.SchedulesService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StationsRepository stationsRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoutesService routesService;
    @Autowired
    private SchedulesService schedulesService;

    @Override
    public void run(String... args) throws Exception {
        if (stationsRepository.count() > 0) return;

        RoutesRequest routesRequest = new RoutesRequest(
                "R1",
                "Bến xe Miền Đông - Bến Thành",
                20
        );

       RoutesResponse routesResponse = routesService.createRoute(routesRequest);
        Routes route = modelMapper.map(routesResponse, Routes.class);

        List<Stations> stations = List.of(
                create("BXMD", "Bến xe Miền Đông", "Xa lộ Hà Nội, P. Tân Phú, TP Thủ Đức", 10.870, 106.807, 1, route),
                create("ST", "Suối Tiên", "Xa lộ Hà Nội, P. Linh Trung, TP Thủ Đức", 10.867, 106.800, 2, route),
                create("CNC", "Khu Công nghệ cao", "Xa lộ Hà Nội, P. Hiệp Phú, TP Thủ Đức", 10.860, 106.789, 3, route),
                create("TDU", "Thủ Đức", "Xa lộ Hà Nội, P. Bình Thọ, TP Thủ Đức", 10.850, 106.782, 4, route),
                create("BTH", "Bình Thái", "Xa lộ Hà Nội, P. Trường Thọ, TP Thủ Đức", 10.843, 106.776, 5, route),
                create("PL", "Phước Long", "Xa lộ Hà Nội, P. Phước Long A, TP Thủ Đức", 10.833, 106.765, 6, route),
                create("RC", "Rạch Chiếc", "Xa lộ Hà Nội, P. An Phú, TP Thủ Đức", 10.824, 106.752, 7, route),
                create("AP", "An Phú", "Xa lộ Hà Nội, P. An Phú, TP Thủ Đức", 10.817, 106.741, 8, route),
                create("TD", "Thảo Điền", "Xa lộ Hà Nội, P. Thảo Điền, TP Thủ Đức", 10.810, 106.735, 9, route),
                create("TC", "Tân Cảng", "Xa lộ Hà Nội, P. 22, Q. Bình Thạnh", 10.805, 106.722, 10, route),
                create("CVVT", "Công viên Văn Thánh", "Nguyễn Hữu Cảnh, P. 22, Q. Bình Thạnh", 10.798, 106.715, 11, route),
                create("BS", "Ba Son", "Tôn Đức Thắng, P. Bến Nghé, Q.1", 10.783, 106.705, 12, route),
                create("NHTP", "Nhà hát Thành phố", "Lê Lợi, P. Bến Nghé, Q.1", 10.777, 106.702, 13, route),
                create("BT", "Bến Thành", "Lê Lợi, P. Bến Thành, Q.1", 10.772, 106.698, 14, route)
        );

       List<Stations> savedStations= stationsRepository.saveAll(stations);

        createSchedulesForStations(savedStations);

    }

    private Stations create(String code, String name, String address, double lat, double lng, int order, Routes route) {
        Stations station = new Stations();
        station.setStationCode(code);
        station.setName(name);
        station.setAddress(address);
        station.setLatitude(BigDecimal.valueOf(lat));
        station.setLongitude(BigDecimal.valueOf(lng));
        station.setSequenceOrder(order);
        station.setStatus(Stations.Status.open);
        station.setCreatedAt(LocalDateTime.now());
        station.setUpdatedAt(LocalDateTime.now());
        station.setRoute(route);
        return station;
    }

    private void createSchedulesForStations(List<Stations> stations) {
        // Thời gian bắt đầu và kết thúc hoạt động
        LocalTime startTime = LocalTime.of(5, 0); // 5:00 AM
        LocalTime endTime = LocalTime.of(22, 0);  // 10:00 PM

        // Tạo schedule cho hướng đi (forward)
        createSchedulesForDirection(stations, startTime, endTime, Schedules.Direction.forward);

        // Tạo schedule cho hướng về (backward)
        createSchedulesForDirection(stations, startTime, endTime, Schedules.Direction.backward);
    }
    private void createSchedulesForDirection(List<Stations> stations, LocalTime startTime, LocalTime endTime, Schedules.Direction direction) {
        LocalTime currentTime = startTime;

        while (currentTime.isBefore(endTime) || currentTime.equals(endTime)) {
            // Tính toán thời gian cho từng trạm
            LocalTime tripStartTime = currentTime;

            for (int i = 0; i < stations.size(); i++) {
                Stations station = stations.get(i);

                // Tính thời gian đến và đi dựa trên thứ tự trạm
                LocalTime arrivalTime;
                LocalTime departureTime;

                if (direction == Schedules.Direction.forward) {
                    // Hướng đi: từ trạm đầu đến trạm cuối
                    arrivalTime = tripStartTime.plusMinutes(i * 2L); // Mỗi trạm cách nhau 2 phút
                    departureTime = arrivalTime.plusMinutes(1); // Dừng 1 phút tại mỗi trạm
                } else {
                    // Hướng về: từ trạm cuối về trạm đầu
                    int reverseIndex = stations.size() - 1 - i;
                    arrivalTime = tripStartTime.plusMinutes(reverseIndex * 2L);
                    departureTime = arrivalTime.plusMinutes(1);
                }

                // Tạo schedule request
                SchedulesRequest scheduleRequest = new SchedulesRequest();
                scheduleRequest.setStationId(station.getStationId());
                scheduleRequest.setTimeArrival(arrivalTime);
                scheduleRequest.setTimeDeparture(departureTime);
                scheduleRequest.setDirection(direction);
                scheduleRequest.setDescription(String.format("Chuyến %s lúc %s - Trạm %s",
                        direction == Schedules.Direction.forward ? "đi" : "về",
                        tripStartTime.toString(),
                        station.getName()));

                try {
                    schedulesService.createSchedule(scheduleRequest);
                } catch (Exception e) {
                    System.err.println("Error creating schedule for station " + station.getName() + ": " + e.getMessage());
                }
            }

            // Chuyến tiếp theo sau 30 phút
            currentTime = currentTime.plusMinutes(30);
        }
    }
}

