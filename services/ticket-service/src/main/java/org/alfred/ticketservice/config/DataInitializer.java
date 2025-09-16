/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.alfred.ticketservice.dto.fare_matrix.FareMatrixRequest;
import org.alfred.ticketservice.model.FarePricing;
import org.alfred.ticketservice.model.TicketTypes;
import org.alfred.ticketservice.repository.FareMatrixRepository;
import org.alfred.ticketservice.repository.FarePricingRepository;
import org.alfred.ticketservice.repository.TicketTypeRepository;
import org.alfred.ticketservice.service.FareMatrixService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final TicketTypeRepository ticketTypeRepository;
  private final FareMatrixRepository fareMatrixRepository;
  private final FarePricingRepository farePricingRepository;
  private final FareMatrixService fareMatrixService;

  @Override
  public void run(String... args) {
    initTicketTypes();
    initFareMatrix();
  }

  private void initTicketTypes() {
    if (ticketTypeRepository.count() > 0) return;

    List<TicketTypes> ticketTypes =
        List.of(
            TicketTypes.builder()
                .name("One Day")
                .description("Vé 1 ngày")
                .validityDuration(1) // 1 day in minutes
                .price(20000f)
                .isActive(true)
                .build(),
            TicketTypes.builder()
                .name("Three Days")
                .description("Vé 3 ngày")
                .validityDuration(3) // 3 days in minutes
                .price(50000f)
                .isActive(true)
                .build(),
            TicketTypes.builder()
                .name("One Week")
                .description("Vé tuần")
                .validityDuration(7) // 7 days in minutes
                .price(100000f)
                .isActive(true)
                .build(),
            TicketTypes.builder()
                .name("One Month")
                .description("Vé tháng")
                .validityDuration(30) // 30 days in minutes
                .price(300000f)
                .isActive(true)
                .build(),
            TicketTypes.builder()
                .name("Single")
                .description("Vé đơn")
                .validityDuration(0) // 30 days in minutes
                .price(0f)
                .isActive(true)
                .build(),
            TicketTypes.builder()
                .name("Student")
                .description("Vé sinh viên")
                .validityDuration(30) // 30 days in minutes
                .forStudent(true)
                .price(150000f)
                .isActive(true)
                .build());
    ticketTypeRepository.saveAll(ticketTypes);
  }

  private void initFareMatrix() {
    if (farePricingRepository.count() > 0) return;
    List<FarePricing> list =
        List.of(
            FarePricing.builder()
                .minDistanceKm(0)
                .maxDistanceKm(5)
                .price(12000f)
                .isActive(true)
                .build(),
            FarePricing.builder()
                .minDistanceKm(5)
                .maxDistanceKm(10)
                .price(15000f)
                .isActive(true)
                .build(),
            FarePricing.builder()
                .minDistanceKm(10)
                .maxDistanceKm(15)
                .price(18000f)
                .isActive(true)
                .build(),
            FarePricing.builder()
                .minDistanceKm(15)
                .maxDistanceKm(20)
                .price(21000f)
                .isActive(true)
                .build(),
            FarePricing.builder()
                .minDistanceKm(20)
                .maxDistanceKm(25)
                .price(24000f)
                .isActive(true)
                .build(),
            FarePricing.builder()
                .minDistanceKm(25)
                .maxDistanceKm(30)
                .price(27000f)
                .isActive(true)
                .build());
    farePricingRepository.saveAll(list);

    if (fareMatrixRepository.count() > 0) return;

    Long bxmdId = 1L;
    String bxmdName = "Bến xe Miền Đông";
    Long stId = 2L;
    String stName = "Trường Đại học Quốc gia";
    Long cncId = 3L;
    String cncName = "Khu Công nghệ cao";
    Long tduId = 4L;
    String tduName = "Thủ Đức";
    Long bthId = 5L;
    String bthName = "Bình Thái";
    Long plId = 6L;
    String plName = "Phước Long";
    Long rcId = 7L;
    String rcName = "Rạch Chiếc";
    Long apId = 8L;
    String apName = "An Phú";
    Long tdId = 9L;
    String tdName = "Thảo Điền";
    Long tcId = 10L;
    String tcName = "Tân Cảng";
    Long cvvt = 11L;
    String cvvtName = "Công viên Văn Thánh";
    Long bsId = 12L;
    String bsName = "Ba Son";
    Long nhtpId = 13L;
    String nhtpName = "Nhà hát Thành phố";
    Long btId = 14L;
    String btName = "Bến Thành";

    List<FareMatrixRequest> fares =
        List.of(
            // From BXMD (1)
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + stName)
                .startStationId(bxmdId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + cncName)
                .startStationId(bxmdId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + tduName)
                .startStationId(bxmdId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + bthName)
                .startStationId(bxmdId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + plName)
                .startStationId(bxmdId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + rcName)
                .startStationId(bxmdId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + apName)
                .startStationId(bxmdId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + tdName)
                .startStationId(bxmdId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + tcName)
                .startStationId(bxmdId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + cvvtName)
                .startStationId(bxmdId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + bsName)
                .startStationId(bxmdId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + nhtpName)
                .startStationId(bxmdId)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bxmdName + "-" + btName)
                .startStationId(bxmdId)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From ST (2)
            FareMatrixRequest.builder()
                .name(stName + "-" + bxmdName)
                .startStationId(stId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + cncName)
                .startStationId(stId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + tduName)
                .startStationId(stId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + bthName)
                .startStationId(stId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + plName)
                .startStationId(stId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + rcName)
                .startStationId(stId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + apName)
                .startStationId(stId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + tdName)
                .startStationId(stId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + tcName)
                .startStationId(stId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + cvvtName)
                .startStationId(stId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + bsName)
                .startStationId(stId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + nhtpName)
                .startStationId(stId)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(stName + "-" + btName)
                .startStationId(stId)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From CNC (3)
            FareMatrixRequest.builder()
                .name(cncName + "-" + bxmdName)
                .startStationId(cncId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + stName)
                .startStationId(cncId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + tduName)
                .startStationId(cncId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + bthName)
                .startStationId(cncId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + plName)
                .startStationId(cncId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + rcName)
                .startStationId(cncId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + apName)
                .startStationId(cncId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + tdName)
                .startStationId(cncId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + tcName)
                .startStationId(cncId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + cvvtName)
                .startStationId(cncId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + bsName)
                .startStationId(cncId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + nhtpName)
                .startStationId(cncId)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cncName + "-" + btName)
                .startStationId(cncId)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From TDU (4)
            FareMatrixRequest.builder()
                .name(tduName + "-" + bxmdName)
                .startStationId(tduId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + stName)
                .startStationId(tduId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + cncName)
                .startStationId(tduId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + bthName)
                .startStationId(tduId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + plName)
                .startStationId(tduId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + rcName)
                .startStationId(tduId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + apName)
                .startStationId(tduId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + tdName)
                .startStationId(tduId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + tcName)
                .startStationId(tduId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + cvvtName)
                .startStationId(tduId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + bsName)
                .startStationId(tduId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + nhtpName)
                .startStationId(tduId)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tduName + "-" + btName)
                .startStationId(tduId)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From BTH (5)
            FareMatrixRequest.builder()
                .name(bthName + "-" + bxmdName)
                .startStationId(bthId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + stName)
                .startStationId(bthId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + cncName)
                .startStationId(bthId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + tduName)
                .startStationId(bthId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + plName)
                .startStationId(bthId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + rcName)
                .startStationId(bthId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + apName)
                .startStationId(bthId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + tdName)
                .startStationId(bthId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + tcName)
                .startStationId(bthId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + cvvtName)
                .startStationId(bthId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + bsName)
                .startStationId(bthId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + nhtpName)
                .startStationId(bthId)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bthName + "-" + btName)
                .startStationId(bthId)
                .endStationId(btId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + bxmdName)
                .startStationId(plId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + stName)
                .startStationId(plId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + cncName)
                .startStationId(plId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + tduName)
                .startStationId(plId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + bthName)
                .startStationId(plId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + rcName)
                .startStationId(plId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + apName)
                .startStationId(plId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + tdName)
                .startStationId(plId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + tcName)
                .startStationId(plId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + cvvtName)
                .startStationId(plId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + bsName)
                .startStationId(plId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + nhtpName)
                .startStationId(plId)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(plName + "-" + btName)
                .startStationId(plId)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From RC (7)
            FareMatrixRequest.builder()
                .name(rcName + "-" + bxmdName)
                .startStationId(rcId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + stName)
                .startStationId(rcId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + cncName)
                .startStationId(rcId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + tduName)
                .startStationId(rcId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + bthName)
                .startStationId(rcId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + plName)
                .startStationId(rcId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + apName)
                .startStationId(rcId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + tdName)
                .startStationId(rcId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + tcName)
                .startStationId(rcId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + cvvtName)
                .startStationId(rcId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + bsName)
                .startStationId(rcId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + nhtpName)
                .startStationId(rcId)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(rcName + "-" + btName)
                .startStationId(rcId)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From AP (8)
            FareMatrixRequest.builder()
                .name(apName + "-" + bxmdName)
                .startStationId(apId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + stName)
                .startStationId(apId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + cncName)
                .startStationId(apId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + tduName)
                .startStationId(apId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + bthName)
                .startStationId(apId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + plName)
                .startStationId(apId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + rcName)
                .startStationId(apId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + tdName)
                .startStationId(apId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + tcName)
                .startStationId(apId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + cvvtName)
                .startStationId(apId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + bsName)
                .startStationId(apId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + nhtpName)
                .startStationId(apId)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(apName + "-" + btName)
                .startStationId(apId)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From TD (9)
            FareMatrixRequest.builder()
                .name(tdName + "-" + bxmdName)
                .startStationId(tdId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + stName)
                .startStationId(tdId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + cncName)
                .startStationId(tdId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + tduName)
                .startStationId(tdId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + bthName)
                .startStationId(tdId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + plName)
                .startStationId(tdId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + rcName)
                .startStationId(tdId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + apName)
                .startStationId(tdId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + tcName)
                .startStationId(tdId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + cvvtName)
                .startStationId(tdId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + bsName)
                .startStationId(tdId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + nhtpName)
                .startStationId(tdId)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tdName + "-" + btName)
                .startStationId(tdId)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From TC (10)
            FareMatrixRequest.builder()
                .name(tcName + "-" + bxmdName)
                .startStationId(tcId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + stName)
                .startStationId(tcId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + cncName)
                .startStationId(tcId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + tduName)
                .startStationId(tcId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + bthName)
                .startStationId(tcId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + plName)
                .startStationId(tcId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + rcName)
                .startStationId(tcId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + apName)
                .startStationId(tcId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + tdName)
                .startStationId(tcId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + cvvtName)
                .startStationId(tcId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + bsName)
                .startStationId(tcId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + nhtpName)
                .startStationId(tcId)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(tcName + "-" + btName)
                .startStationId(tcId)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From CVVT (11)
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + bxmdName)
                .startStationId(cvvt)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + stName)
                .startStationId(cvvt)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + cncName)
                .startStationId(cvvt)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + tduName)
                .startStationId(cvvt)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + bthName)
                .startStationId(cvvt)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + plName)
                .startStationId(cvvt)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + rcName)
                .startStationId(cvvt)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + apName)
                .startStationId(cvvt)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + tdName)
                .startStationId(cvvt)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + tcName)
                .startStationId(cvvt)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + bsName)
                .startStationId(cvvt)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + nhtpName)
                .startStationId(cvvt)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(cvvtName + "-" + btName)
                .startStationId(cvvt)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From BS (12)
            FareMatrixRequest.builder()
                .name(bsName + "-" + bxmdName)
                .startStationId(bsId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + stName)
                .startStationId(bsId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + cncName)
                .startStationId(bsId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + tduName)
                .startStationId(bsId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + bthName)
                .startStationId(bsId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + plName)
                .startStationId(bsId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + rcName)
                .startStationId(bsId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + apName)
                .startStationId(bsId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + tdName)
                .startStationId(bsId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + tcName)
                .startStationId(bsId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + cvvtName)
                .startStationId(bsId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + nhtpName)
                .startStationId(bsId)
                .endStationId(nhtpId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(bsName + "-" + btName)
                .startStationId(bsId)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From NHTP (13)
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + bxmdName)
                .startStationId(nhtpId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + stName)
                .startStationId(nhtpId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + cncName)
                .startStationId(nhtpId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + tduName)
                .startStationId(nhtpId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + bthName)
                .startStationId(nhtpId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + plName)
                .startStationId(nhtpId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + rcName)
                .startStationId(nhtpId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + apName)
                .startStationId(nhtpId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + tdName)
                .startStationId(nhtpId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + tcName)
                .startStationId(nhtpId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + cvvtName)
                .startStationId(nhtpId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + bsName)
                .startStationId(nhtpId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(nhtpName + "-" + btName)
                .startStationId(nhtpId)
                .endStationId(btId)
                .isActive(true)
                .build(),

            // From BT (14)
            FareMatrixRequest.builder()
                .name(btName + "-" + bxmdName)
                .startStationId(btId)
                .endStationId(bxmdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + stName)
                .startStationId(btId)
                .endStationId(stId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + cncName)
                .startStationId(btId)
                .endStationId(cncId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + tduName)
                .startStationId(btId)
                .endStationId(tduId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + bthName)
                .startStationId(btId)
                .endStationId(bthId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + plName)
                .startStationId(btId)
                .endStationId(plId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + rcName)
                .startStationId(btId)
                .endStationId(rcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + apName)
                .startStationId(btId)
                .endStationId(apId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + tdName)
                .startStationId(btId)
                .endStationId(tdId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + tcName)
                .startStationId(btId)
                .endStationId(tcId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + cvvtName)
                .startStationId(btId)
                .endStationId(cvvt)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + bsName)
                .startStationId(btId)
                .endStationId(bsId)
                .isActive(true)
                .build(),
            FareMatrixRequest.builder()
                .name(btName + "-" + nhtpName)
                .startStationId(btId)
                .endStationId(nhtpId)
                .isActive(true)
                .build());
    for (FareMatrixRequest fare : fares) {
      fareMatrixService.createFareMatrix(fare);
    }
  }
}
