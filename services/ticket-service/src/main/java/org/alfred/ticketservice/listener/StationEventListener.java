package org.alfred.ticketservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alfred.ticketservice.config.RabbitMQConfig;
import org.alfred.ticketservice.dto.StationEvent;
import org.alfred.ticketservice.service.TicketReactionService;
import org.alfred.ticketservice.service.TicketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StationEventListener {

    private final TicketReactionService ticketService;

    @RabbitListener(queues = RabbitMQConfig.TICKET_STATION_DELETE_QUEUE)
    public void handleStationDeleted(StationEvent event) {
        try {
            log.info("Received station deleted event: {}", event);
            ticketService.handleDeleteStation(event);
            // Xử lý khi station bị xóa
            // Ví dụ: hủy tất cả tickets liên quan đến station này
//            ticketService.handleStationDeleted(event.getStationId());

//            log.info("Successfully processed station deleted event for station ID: {}", event.getStationId());

        } catch (Exception e) {
            log.error("Error processing station deleted event: {}", event, e);
            throw e; // Re-throw để message được gửi vào DLQ
        }
    }

    @RabbitListener(queues = RabbitMQConfig.TICKET_STATION_ADD_QUEUE)
    public void handleStationAdded(StationEvent event) {
        try {
            log.info("Received station added event: {}", event);
            ticketService.handleAddStation(event);
            // Xử lý khi station được thêm mới
            // Ví dụ: cập nhật cache hoặc thực hiện logic khác
//            ticketService.handleStationAdded(event);

//            log.info("Successfully processed station added event for station ID: {}", event.getStationId());
        } catch (Exception e) {
            log.error("Error processing station added event: {}", event, e);
            throw e; // Re-throw để message được gửi vào DLQ
        }
    }
}