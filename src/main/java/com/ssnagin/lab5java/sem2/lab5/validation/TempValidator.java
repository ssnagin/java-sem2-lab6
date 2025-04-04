package com.ssnagin.lab5java.sem2.lab5.validation;

import com.ssnagin.lab5java.sem2.lab5.collection.model.Album;
import com.ssnagin.lab5java.sem2.lab5.collection.model.Coordinates;
import com.ssnagin.lab5java.sem2.lab5.collection.model.MusicBand;
import com.ssnagin.lab5java.sem2.lab5.validation.annotations.*;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TempValidator {

    public static List<String> validateMusicBand(MusicBand band) {

        List<String> errors = new ArrayList<>();

        if (band == null) {
            errors.add("MusicBand object is null");
            return errors;
        }

        // Validate individual fields
        validateField(band, "id", errors);
        validateField(band, "name", errors);
        validateField(band, "coordinates", errors);
        validateField(band, "numberOfParticipants", errors);
        validateField(band, "singlesCount", errors);
        validateField(band, "genre", errors);
        validateField(band, "bestAlbum", errors);

        // If coordinates exist, validate them
        if (band.getCoordinates() != null) {
            List<String> coordErrors = validateCoordinates(band.getCoordinates());
            if (!coordErrors.isEmpty()) {
                errors.add("Coordinates validation errors: " + String.join(", ", coordErrors));
            }
        }

        // If bestAlbum exists, validate it
        if (band.getBestAlbum() != null) {
            List<String> albumErrors = validateAlbum(band.getBestAlbum());
            if (!albumErrors.isEmpty()) {
                errors.add("Album validation errors: " + String.join(", ", albumErrors));
            }
        }

        return errors;
    }

    public static List<String> validateCoordinates(Coordinates coordinates) {
        List<String> errors = new ArrayList<>();

        if (coordinates == null) {
            errors.add("Coordinates object is null");
            return errors;
        }

        validateField(coordinates, "x", errors);
        validateField(coordinates, "y", errors);

        return errors;
    }

    public static List<String> validateAlbum(Album album) {
        List<String> errors = new ArrayList<>();

        if (album == null) {
            errors.add("Album object is null");
            return errors;
        }

        validateField(album, "name", errors);
        validateField(album, "tracks", errors);

        return errors;
    }

    private static void validateField(Object object, String fieldName, List<String> errors) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(object);

            // Check @NotNull annotation
            if (field.isAnnotationPresent(NotNull.class)) {
                if (value == null) {
                    errors.add(fieldName + " cannot be null");
                    return; // No need to check other annotations if field is null
                }
            }

            // Check @NotEmpty annotation (only for String fields)
            if (field.isAnnotationPresent(NotEmpty.class) && value instanceof String) {
                if (((String) value).isEmpty()) {
                    errors.add(fieldName + " cannot be empty");
                }
            }

            // Check @PositiveNumber annotation
            if (field.isAnnotationPresent(PositiveNumber.class) && value != null) {
                if (value instanceof Number) {
                    if (((Number) value).longValue() <= 0) {
                        errors.add(fieldName + " must be a positive number");
                    }
                }
            }

            // Check @NegativeNumber annotation
            if (field.isAnnotationPresent(NegativeNumber.class) && value != null) {
                if (value instanceof Number) {
                    if (((Number) value).longValue() > 0) {
                        errors.add(fieldName + " must be a negative number");
                    }
                }
            }

            // Check @MaxValue annotation
            if (field.isAnnotationPresent(MaxValue.class) && value != null) {
                MaxValue annotation = field.getAnnotation(MaxValue.class);
                if (value instanceof Number) {
                    long numValue = ((Number) value).longValue();
                    if (numValue > annotation.value()) {
                        errors.add(fieldName + " must be less than or equal to " + annotation.value());
                    }
                }
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            errors.add("Error validating field " + fieldName + ": " + e.getMessage());
        }
    }

    public static boolean isValidMusicBand(MusicBand band) {
        return validateMusicBand(band).isEmpty();
    }

    public static boolean isValidCoordinates(Coordinates coordinates) {
        return validateCoordinates(coordinates).isEmpty();
    }

    public static boolean isValidAlbum(Album album) {
        return validateAlbum(album).isEmpty();
    }
}