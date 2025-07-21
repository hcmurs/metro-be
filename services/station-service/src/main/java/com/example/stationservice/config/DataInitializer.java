package com.example.stationservice.config;


import com.example.stationservice.dto.RoutesRequest;
import com.example.stationservice.dto.RoutesResponse;
import com.example.stationservice.dto.SchedulesRequest;
import com.example.stationservice.model.Routes;
import com.example.stationservice.model.Schedules;
import com.example.stationservice.model.StationRoute;
import com.example.stationservice.model.Stations;
import com.example.stationservice.repository.StationRouteRepository;
import com.example.stationservice.repository.StationsRepository;
import com.example.stationservice.service.RoutesService;
import com.example.stationservice.service.SchedulesService;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final StationsRepository stationsRepository;
    private final StationRouteRepository stationRouteRepository;
    private final ModelMapper modelMapper;
    private final RoutesService routesService;
    private final SchedulesService schedulesService;

    @Override
    public void run(String... args) throws Exception {
        if (stationsRepository.count() > 0) return;

        RoutesRequest routesRequest = new RoutesRequest(

                "Bến xe Miền Đông - Bến Thành",
                "R1",
                20
        );


       RoutesResponse routesResponse = routesService.createRoute(routesRequest);
        Routes route = modelMapper.map(routesResponse, Routes.class);

        List<Stations> stations = List.of(
                create("BXMD", "Bến xe Miền Đông", "Bến xe Miền Đông mới (Suối Tiên)", 10.879512241390108, 106.81409560253722),
                create("NHTP", "Trường Đại học Quốc gia (National University)", "Linh Trung, Thủ Đức", 10.866291781126687, 106.80124945301374),
                create("HTP", "Khu Công nghệ cao (Hi-tech Park)", "Thủ Đức", 10.858999986669915, 106.78878017918157),
                create("TDU", "Thủ Đức", "Bình Thọ, Thủ Đức", 10.846372121206313, 106.77159999068043),
                create("BTH", "Bình Thái", "Trường Thọ, Thủ Đức", 10.832601114841145, 106.76384478331777),
                create("PL", "Phước Long", "Phước Long A, Thủ Đức", 10.821356644499083, 106.75813941185976),
                create("RC", "Rạch Chiếc", "An Phú, Thủ Đức", 10.808476742610557, 106.75525715663437),
                create("AP", "An Phú", "An Phú, Thủ Đức", 10.802069265420103, 106.74227526515422),
                create("TD", "Thảo Điền", "Thảo Điền, Thủ Đức", 10.800467374750985, 106.73367073862954),
                create("TC", "Tân Cảng", "Bình Thạnh", 10.798550415101449, 106.72329887103433),
                create("CVVT", "Công viên Văn Thánh (Văn Thánh Park)", "Bình Thạnh", 10.796069240758085, 106.71553225518468),
                create("BS", "Ba Son", "Bình Thạnh", 10.781570288538946, 106.70799481205246),
                create("NHTP", "Nhà hát Thành phố (Opera House)", "Q.1", 10.776254445807423, 106.70298994292116),
                create("BT", "Bến Thành", "Q.1", 10.77099020206032, 106.69837125466249)

        );

       List<Stations> savedStations= stationsRepository.saveAll(stations);
       List<StationRoute> existingRoutes = new ArrayList<>();
       for (Stations station : savedStations) {
           StationRoute stationRoute = new StationRoute();
              stationRoute.setStation(station);
              stationRoute.setRoute(route);
              stationRoute.setSequenceOrder(savedStations.indexOf(station) + 1);
              stationRouteRepository.save(stationRoute);
           existingRoutes.add(stationRoute);
       }
        createSchedulesForStations(existingRoutes);
    }

    private Stations create(String code, String name, String address, double lat, double lng) {
        Stations station = new Stations();
        station.setStationCode(code);
        station.setName(name);
        station.setAddress(address);
        station.setLatitude(lat);
        station.setLongitude(lng);
        station.setStatus(Stations.Status.active);
        station.setDeleted(false);
        station.setCreatedAt(LocalDateTime.now());
        station.setUpdatedAt(LocalDateTime.now());
        return station;
    }



    private void createSchedulesForStations(List<StationRoute> stations) {
        // Thời gian bắt đầu và kết thúc hoạt động
        LocalTime startTime = LocalTime.of(5, 0); // 5:00 AM
        LocalTime endTime = LocalTime.of(22, 0);  // 10:00 PM

        // Tạo schedule cho hướng đi (forward)
        createSchedulesForDirection(stations, startTime, endTime, Schedules.Direction.forward);

        // Tạo schedule cho hướng về (backward)
        createSchedulesForDirection(stations, startTime, endTime, Schedules.Direction.backward);
    }
    private void createSchedulesForDirection(List<StationRoute> stations, LocalTime startTime, LocalTime endTime, Schedules.Direction direction) {
        LocalTime currentTime = startTime;

        while (currentTime.isBefore(endTime) || currentTime.equals(endTime)) {
            // Tính toán thời gian cho từng trạm
            LocalTime tripStartTime = currentTime;

            for (int i = 0; i < stations.size(); i++) {
                StationRoute station = stations.get(i);

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
                scheduleRequest.setStationId(station.getId());
                scheduleRequest.setTimeArrival(arrivalTime);
                scheduleRequest.setTimeDeparture(departureTime);
                scheduleRequest.setDirection(direction);
                scheduleRequest.setDescription(String.format("Chuyến %s lúc %s - Trạm",
                        direction == Schedules.Direction.forward ? "đi" : "về",
                        tripStartTime.toString())
                        );

                try {
                    schedulesService.createSchedule(scheduleRequest);
                } catch (Exception e) {
                    System.err.println("Error creating schedule for station " + ": " + e.getMessage());
                }
            }

            // Chuyến tiếp theo sau 30 phút
            currentTime = currentTime.plusMinutes(30);
        }
    }
}

