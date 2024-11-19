package com.system.creditOnline.service.impl;

import com.system.creditOnline.entity.User;
import com.system.creditOnline.repository.UserRepository;
import com.system.creditOnline.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        log.info("Registrando usuario con ID: {}", user.getId());

        // Calcular la edad
        int age = Period.between(user.getBirthdate(), LocalDate.now()).getYears();
        log.debug("Edad calculada: {}", age);

        if (age < 18 || age > 90) {
            log.error("Edad no permitida. Edad calculada: {}", age);
            throw new IllegalArgumentException("Edad no permitida. Debe ser mayor de 18 años y menor de 90 años.");
        }

        // Guardar usuario
        user.setCreditLine(calculateCreditLine(age));
        log.info("Línea de crédito asignada: {}", user.getCreditLine());

        return userRepository.save(user);
    }

    @Override
    public int calculateCreditLine(int age) {
        log.info("Calculando línea de crédito para la edad: {}", age);

        if (age >= 18 && age <= 25) {
            log.debug("Línea de crédito asignada: 3000");
            return 3000;
        } else if (age > 25 && age <= 30) {
            log.debug("Línea de crédito asignada: 5000");
            return 5000;
        } else if (age > 30 && age <= 65) {
            log.debug("Línea de crédito asignada: 8000");
            return 8000;
        } else if (age > 65 && age <= 90) {
            log.debug("Línea de crédito asignada: 1500");
            return 1500;
        } else {
            log.error("Edad fuera de rango: {}", age);
            throw new IllegalArgumentException("La edad debe estar entre 18 y 90 años.");
        }
    }
}

