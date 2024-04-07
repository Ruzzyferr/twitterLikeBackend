package com.ruzzyfer.twitterlike.deSerialazier;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserDeserializer extends StdDeserializer<User> {

    public UserDeserializer() {
        this(null);
    }

    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        int id = (Integer) ((IntNode) node.get("id")).numberValue();
        String username = node.get("username").asText();
        String email = node.get("email").asText();
        String password = node.get("password").asText();
        // Role deserialize edilir
        Role role = Role.valueOf(node.get("role").asText());

        // Yetkiler çıkarılır
        List<GrantedAuthority> authorities = new ArrayList<>();
        JsonNode authoritiesNode = node.get("authorities");
        if (authoritiesNode != null && authoritiesNode.isArray()) {
            for (JsonNode authorityNode : authoritiesNode) {
                authorities.add(new SimpleGrantedAuthority(authorityNode.get("authority").asText()));
            }
        }

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
