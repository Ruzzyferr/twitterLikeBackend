package com.ruzzyfer.twitterlike.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruzzyfer.twitterlike.entity.UserInteraction;
import com.ruzzyfer.twitterlike.repository.UserInteractionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DailyUserInteractionConsumer {

    private final UserInteractionRepository userInteractionRepository;

    public DailyUserInteractionConsumer(UserInteractionRepository userInteractionRepository) {
        this.userInteractionRepository = userInteractionRepository;
    }

    //Gelecek Örnek veri
//    {
//        "userId": {
//        "id": 123,
//                "username": "exampleUser",
//                "email": "user@example.com",
//                "password": "examplePassword",
//                "role": "USER"
//    },
//        "interactionType": "LIKE",
//            "category": "SPORT",
//            "interactionCount": 1,
//            "date": "2024-02-15"
//    }

    @KafkaListener(topics = "user-interaction-topic", groupId = "group_id")
    public void consumeInteractions(String interactionJson) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserInteraction interaction = objectMapper.readValue(interactionJson, UserInteraction.class);
        processInteraction(interaction);
    }

    private void processInteraction(UserInteraction interaction)throws Exception {

        UserInteraction existingInteraction = userInteractionRepository.findByUseridAndCategoryAndInteractionTypeAndDate(
                interaction.getUserid(),
                interaction.getCategory(),
                interaction.getInteractionType(),
                interaction.getDate());

        // Eğer varsa, mevcut veriyi güncelle
        if (existingInteraction != null) {
            existingInteraction.setDailyActiveMinutes(existingInteraction.getDailyActiveMinutes() + 1);
            existingInteraction.setInteractionCount(existingInteraction.getInteractionCount() + 1);
            saveInteractionToDatabase(existingInteraction);
        } else {
            // Yoksa, yeni bir veri oluştur
            interaction.setDailyActiveMinutes(1);
            interaction.setInteractionCount(1);
            saveInteractionToDatabase(interaction);
        }
    }

    private void saveInteractionToDatabase(UserInteraction interaction) {
        userInteractionRepository.save(interaction);
    }

}
