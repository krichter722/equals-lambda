/**
 * Copyright 2018 Karl-Philipp Richter
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.richtercloud.equals.lambda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richter
 */
public class EqualsLambdaTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EqualsLambdaTest.class);

    /**
     * Test of equals method, of class EqualsLambda.
     */
    @Test
    public void testEqualsCheckNull() {
        LOGGER.info("equals");
        assertThrows(IllegalArgumentException.class,
            () -> EqualsLambda.equals(new Object(), new Object(), null));
    }

    @Test
    public void testEqualsSetCheckNull() {
        LOGGER.info("equals");
        assertThrows(IllegalArgumentException.class,
            () -> EqualsLambda.equalsSet(new HashSet<>(), new HashSet<>(), null));
    }

    @Test
    public void testEqualsListCheckNull() {
        LOGGER.info("equals");
        assertThrows(IllegalArgumentException.class,
            () -> EqualsLambda.equalsList(new LinkedList<>(), new LinkedList<>(), null));
    }

    @Test
    public void testEqualsMapKeyCheckNull() {
        LOGGER.info("equals");
        assertThrows(IllegalArgumentException.class,
            () -> EqualsLambda.equalsMap(new HashMap(), new HashMap(), null, (a,b) -> true));
    }

    @Test
    public void testEqualsMapValueCheckNull() {
        LOGGER.info("equals");
        assertThrows(IllegalArgumentException.class,
            () -> EqualsLambda.equalsMap(new HashMap(), new HashMap(), (a,b) -> true, null));
    }

    @Test
    public void testEqualsTrue() {
        boolean expResult = true;
        boolean result = EqualsLambda.equals("a", "b", (a,b) -> true);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsFalse() {
        boolean expResult = false;
        boolean result = EqualsLambda.equals("a", "b", (a,b) -> false);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsSetTrue() {
        LOGGER.info("equalsSet");
        boolean expResult = true;
        boolean result = EqualsLambda.equalsSet(new HashSet<>(Arrays.asList("a")),
                new HashSet<>(Arrays.asList("b")),
            (a,b) -> true);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsSetFalse() {
        LOGGER.info("equalsSet");
        boolean expResult = false;
        boolean result = EqualsLambda.equalsSet(new HashSet<>(Arrays.asList("a")),
                new HashSet<>(Arrays.asList("b")),
            (a,b) -> false);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsListTrue() {
        LOGGER.info("equalsList");
        boolean expResult = true;
        boolean result = EqualsLambda.equalsList(new LinkedList<>(Arrays.asList("a")),
                new LinkedList<>(Arrays.asList("b")),
            (a,b) -> true);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsListFalse() {
        LOGGER.info("equalsList");
        boolean expResult = false;
        boolean result = EqualsLambda.equalsList(new LinkedList<>(Arrays.asList("a")),
                new LinkedList<>(Arrays.asList("b")),
            (a,b) -> false);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsMapTrueTrue() {
        LOGGER.info("equalsMap");
        boolean expResult = true;
        Map<Object, Object> map0 = new HashMap<>();
        map0.put("1", "2");
        Map<Object, Object> map1 = new HashMap<>();
        map1.put(1, 2);
        boolean result = EqualsLambda.equalsMap(map0, map1, (a,b) -> true, (a,b) -> true);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsMapTrueFalse() {
        LOGGER.info("equalsMap");
        boolean expResult = false;
        Map<Object, Object> map0 = new HashMap<>();
        map0.put("1", "2");
        Map<Object, Object> map1 = new HashMap<>();
        map1.put(1, 2);
        boolean result = EqualsLambda.equalsMap(map0, map1, (a,b) -> true, (a,b) -> false);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsMapFalseTrue() {
        LOGGER.info("equalsMap");
        boolean expResult = false;
        Map<Object, Object> map0 = new HashMap<>();
        map0.put("1", "2");
        Map<Object, Object> map1 = new HashMap<>();
        map1.put(1, 2);
        boolean result = EqualsLambda.equalsMap(map0, map1, (a,b) -> false, (a,b) -> true);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsMapFalseFalse() {
        LOGGER.info("equalsMap");
        boolean expResult = false;
        Map<Object, Object> map0 = new HashMap<>();
        map0.put("1", "2");
        Map<Object, Object> map1 = new HashMap<>();
        map1.put(1, 2);
        boolean result = EqualsLambda.equalsMap(map0, map1, (a,b) -> false, (a,b) -> false);
        assertEquals(expResult, result);
    }
}
