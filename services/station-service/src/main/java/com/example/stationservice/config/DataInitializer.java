package com.example.stationservice.config;


import com.example.stationservice.dto.RoutesRequest;
import com.example.stationservice.dto.RoutesResponse;
import com.example.stationservice.dto.SchedulesRequest;
import com.example.stationservice.model.Routes;
import com.example.stationservice.model.Schedules;
import com.example.stationservice.model.Stations;
import com.example.stationservice.repository.StationsRepository;
import com.example.stationservice.service.RoutesService;
import com.example.stationservice.service.SchedulesService;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final StationsRepository stationsRepository;
    private final ModelMapper modelMapper;
    private final RoutesService routesService;
    private final SchedulesService schedulesService;

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
                create("BXMD", "Bến xe Miền Đông", "Bến xe Miền Đông mới (Suối Tiên)", 10.879444, 106.813889, 1, route),
                create("NHTP", "Trường Đại học Quốc gia (National University)", "Linh Trung, Thủ Đức", 10.866389, 106.801111, 2, route),
                create("HTP", "Khu Công nghệ cao (Hi-tech Park)", "Thủ Đức", 10.860000, 106.789000, 3, route),
                create("TDU", "Thủ Đức", "Bình Thọ, Thủ Đức", 10.850000, 106.782000, 4, route),
                create("BTH", "Bình Thái", "Trường Thọ, Thủ Đức", 10.843000, 106.776000, 5, route),
                create("PL", "Phước Long", "Phước Long A, Thủ Đức", 10.833000, 106.765000, 6, route),
                create("RC", "Rạch Chiếc", "An Phú, Thủ Đức", 10.824000, 106.752000, 7, route),
                create("AP", "An Phú", "An Phú, Thủ Đức", 10.817000, 106.741000, 8, route),
                create("TD", "Thảo Điền", "Thảo Điền, Thủ Đức", 10.810000, 106.735000, 9, route),
                create("TC", "Tân Cảng", "Bình Thạnh", 10.805000, 106.722000, 10, route),
                create("CVVT", "Công viên Văn Thánh (Văn Thánh Park)", "Bình Thạnh", 10.798000, 106.715000, 11, route),
                create("BS", "Ba Son", "Bình Thạnh", 10.783564, 106.709794, 12, route),
                create("NHTP", "Nhà hát Thành phố (Opera House)", "Q.1", 10.777000, 106.702800, 13, route),
                create("BT", "Bến Thành", "Q.1", 10.772000, 106.698000, 14, route)

        );

       List<Stations> savedStations= stationsRepository.saveAll(stations);

        createSchedulesForStations(savedStations);

    }

    private Stations create(String code, String name, String address, double lat, double lng, int order, Routes route) {
        Stations station = new Stations();
        station.setStationCode(code);
        station.setName(name);
        station.setAddress(address);
        station.setLatitude(lat);
        station.setLongitude(lng);
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

