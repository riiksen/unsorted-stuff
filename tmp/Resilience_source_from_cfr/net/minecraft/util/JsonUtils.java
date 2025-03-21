/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSyntaxException
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;

public class JsonUtils {
    private static final String __OBFID = "CL_00001484";

    public static boolean jsonObjectFieldTypeIsString(JsonObject p_151205_0_, String p_151205_1_) {
        return !JsonUtils.jsonObjectFieldTypeIsPrimitive(p_151205_0_, p_151205_1_) ? false : p_151205_0_.getAsJsonPrimitive(p_151205_1_).isString();
    }

    public static boolean jsonElementTypeIsString(JsonElement p_151211_0_) {
        return !p_151211_0_.isJsonPrimitive() ? false : p_151211_0_.getAsJsonPrimitive().isString();
    }

    public static boolean jsonObjectFieldTypeIsArray(JsonObject p_151202_0_, String p_151202_1_) {
        return !JsonUtils.jsonObjectHasNamedField(p_151202_0_, p_151202_1_) ? false : p_151202_0_.get(p_151202_1_).isJsonArray();
    }

    public static boolean jsonObjectFieldTypeIsPrimitive(JsonObject p_151201_0_, String p_151201_1_) {
        return !JsonUtils.jsonObjectHasNamedField(p_151201_0_, p_151201_1_) ? false : p_151201_0_.get(p_151201_1_).isJsonPrimitive();
    }

    public static boolean jsonObjectHasNamedField(JsonObject p_151204_0_, String p_151204_1_) {
        return p_151204_0_ == null ? false : p_151204_0_.get(p_151204_1_) != null;
    }

    public static String getJsonElementStringValue(JsonElement p_151206_0_, String p_151206_1_) {
        if (p_151206_0_.isJsonPrimitive()) {
            return p_151206_0_.getAsString();
        }
        throw new JsonSyntaxException("Expected " + p_151206_1_ + " to be a string, was " + JsonUtils.getJsonElementTypeDescription(p_151206_0_));
    }

    public static String getJsonObjectStringFieldValue(JsonObject p_151200_0_, String p_151200_1_) {
        if (p_151200_0_.has(p_151200_1_)) {
            return JsonUtils.getJsonElementStringValue(p_151200_0_.get(p_151200_1_), p_151200_1_);
        }
        throw new JsonSyntaxException("Missing " + p_151200_1_ + ", expected to find a string");
    }

    public static String getJsonObjectStringFieldValueOrDefault(JsonObject p_151219_0_, String p_151219_1_, String p_151219_2_) {
        return p_151219_0_.has(p_151219_1_) ? JsonUtils.getJsonElementStringValue(p_151219_0_.get(p_151219_1_), p_151219_1_) : p_151219_2_;
    }

    public static boolean getJsonElementBooleanValue(JsonElement p_151216_0_, String p_151216_1_) {
        if (p_151216_0_.isJsonPrimitive()) {
            return p_151216_0_.getAsBoolean();
        }
        throw new JsonSyntaxException("Expected " + p_151216_1_ + " to be a Boolean, was " + JsonUtils.getJsonElementTypeDescription(p_151216_0_));
    }

    public static boolean getJsonObjectBooleanFieldValue(JsonObject p_151212_0_, String p_151212_1_) {
        if (p_151212_0_.has(p_151212_1_)) {
            return JsonUtils.getJsonElementBooleanValue(p_151212_0_.get(p_151212_1_), p_151212_1_);
        }
        throw new JsonSyntaxException("Missing " + p_151212_1_ + ", expected to find a Boolean");
    }

    public static boolean getJsonObjectBooleanFieldValueOrDefault(JsonObject p_151209_0_, String p_151209_1_, boolean p_151209_2_) {
        return p_151209_0_.has(p_151209_1_) ? JsonUtils.getJsonElementBooleanValue(p_151209_0_.get(p_151209_1_), p_151209_1_) : p_151209_2_;
    }

    public static float getJsonElementFloatValue(JsonElement p_151220_0_, String p_151220_1_) {
        if (p_151220_0_.isJsonPrimitive() && p_151220_0_.getAsJsonPrimitive().isNumber()) {
            return p_151220_0_.getAsFloat();
        }
        throw new JsonSyntaxException("Expected " + p_151220_1_ + " to be a Float, was " + JsonUtils.getJsonElementTypeDescription(p_151220_0_));
    }

    public static float getJsonObjectFloatFieldValue(JsonObject p_151217_0_, String p_151217_1_) {
        if (p_151217_0_.has(p_151217_1_)) {
            return JsonUtils.getJsonElementFloatValue(p_151217_0_.get(p_151217_1_), p_151217_1_);
        }
        throw new JsonSyntaxException("Missing " + p_151217_1_ + ", expected to find a Float");
    }

    public static float getJsonObjectFloatFieldValueOrDefault(JsonObject p_151221_0_, String p_151221_1_, float p_151221_2_) {
        return p_151221_0_.has(p_151221_1_) ? JsonUtils.getJsonElementFloatValue(p_151221_0_.get(p_151221_1_), p_151221_1_) : p_151221_2_;
    }

    public static int getJsonElementIntegerValue(JsonElement p_151215_0_, String p_151215_1_) {
        if (p_151215_0_.isJsonPrimitive() && p_151215_0_.getAsJsonPrimitive().isNumber()) {
            return p_151215_0_.getAsInt();
        }
        throw new JsonSyntaxException("Expected " + p_151215_1_ + " to be a Int, was " + JsonUtils.getJsonElementTypeDescription(p_151215_0_));
    }

    public static int getJsonObjectIntegerFieldValue(JsonObject p_151203_0_, String p_151203_1_) {
        if (p_151203_0_.has(p_151203_1_)) {
            return JsonUtils.getJsonElementIntegerValue(p_151203_0_.get(p_151203_1_), p_151203_1_);
        }
        throw new JsonSyntaxException("Missing " + p_151203_1_ + ", expected to find a Int");
    }

    public static int getJsonObjectIntegerFieldValueOrDefault(JsonObject p_151208_0_, String p_151208_1_, int p_151208_2_) {
        return p_151208_0_.has(p_151208_1_) ? JsonUtils.getJsonElementIntegerValue(p_151208_0_.get(p_151208_1_), p_151208_1_) : p_151208_2_;
    }

    public static JsonObject getJsonElementAsJsonObject(JsonElement p_151210_0_, String p_151210_1_) {
        if (p_151210_0_.isJsonObject()) {
            return p_151210_0_.getAsJsonObject();
        }
        throw new JsonSyntaxException("Expected " + p_151210_1_ + " to be a JsonObject, was " + JsonUtils.getJsonElementTypeDescription(p_151210_0_));
    }

    public static JsonObject getJsonObjectFieldOrDefault(JsonObject p_151218_0_, String p_151218_1_, JsonObject p_151218_2_) {
        return p_151218_0_.has(p_151218_1_) ? JsonUtils.getJsonElementAsJsonObject(p_151218_0_.get(p_151218_1_), p_151218_1_) : p_151218_2_;
    }

    public static JsonArray getJsonElementAsJsonArray(JsonElement p_151207_0_, String p_151207_1_) {
        if (p_151207_0_.isJsonArray()) {
            return p_151207_0_.getAsJsonArray();
        }
        throw new JsonSyntaxException("Expected " + p_151207_1_ + " to be a JsonArray, was " + JsonUtils.getJsonElementTypeDescription(p_151207_0_));
    }

    public static JsonArray getJsonObjectJsonArrayField(JsonObject p_151214_0_, String p_151214_1_) {
        if (p_151214_0_.has(p_151214_1_)) {
            return JsonUtils.getJsonElementAsJsonArray(p_151214_0_.get(p_151214_1_), p_151214_1_);
        }
        throw new JsonSyntaxException("Missing " + p_151214_1_ + ", expected to find a JsonArray");
    }

    public static JsonArray getJsonObjectJsonArrayFieldOrDefault(JsonObject p_151213_0_, String p_151213_1_, JsonArray p_151213_2_) {
        return p_151213_0_.has(p_151213_1_) ? JsonUtils.getJsonElementAsJsonArray(p_151213_0_.get(p_151213_1_), p_151213_1_) : p_151213_2_;
    }

    public static String getJsonElementTypeDescription(JsonElement p_151222_0_) {
        String var1 = StringUtils.abbreviateMiddle((String)String.valueOf((Object)p_151222_0_), (String)"...", (int)10);
        if (p_151222_0_ == null) {
            return "null (missing)";
        }
        if (p_151222_0_.isJsonNull()) {
            return "null (json)";
        }
        if (p_151222_0_.isJsonArray()) {
            return "an array (" + var1 + ")";
        }
        if (p_151222_0_.isJsonObject()) {
            return "an object (" + var1 + ")";
        }
        if (p_151222_0_.isJsonPrimitive()) {
            JsonPrimitive var2 = p_151222_0_.getAsJsonPrimitive();
            if (var2.isNumber()) {
                return "a number (" + var1 + ")";
            }
            if (var2.isBoolean()) {
                return "a boolean (" + var1 + ")";
            }
        }
        return var1;
    }
}

