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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author richter
 */
public class EqualsLambda {

    /**
     * Does what
     * {@link java.util.Objects#equals(java.lang.Object, java.lang.Object) }
     * does, except allows to use methods to check equality as lambda.
     *
     * @param <T> allows to restrict the type of arguments to compare
     * @param a the first item
     * @param b the first item
     * @param equalsCheck the equality check to use
     * @return {@code true} if {@code a} and {@code b} are equal to each other,
     * {@code false} otherwise
     */
    public static <T> boolean equals(T a, T b,
            EqualsCheck<T> equalsCheck) {
        if(equalsCheck == null) {
            throw new IllegalArgumentException("equalsCheck mustn't be null");
        }
        return (a == b) || (a != null && equalsCheck.equals(a, b));
    }

    /**
     * Checks that all elements in {@code a} are contained in {@code b} and vice versa.
     *
     * @param <T> allows to restrict the type of arguments to compare
     * @param a the first set
     * @param b the second set
     * @param equalsCheck the equality check to use
     * @return {@code true} if all elements in {@code a} are contained in
     * {@code b} and {@code b} contains no other items, {@code false} otherwise
     */
    public static <T> boolean equalsSet(Set<T> a, Set<T> b,
            EqualsCheck<T> equalsCheck) {
        if(equalsCheck == null) {
            throw new IllegalArgumentException("equalsCheck mustn't be null");
        }
        if(a == b) {
            return true;
        }
        if(a.stream().anyMatch(a0 -> b.stream().noneMatch(b0 -> equalsCheck.equals(a0, b0)))) {
            return false;
        }
        if(b.stream().anyMatch(b0 -> a.stream().noneMatch(a0 -> equalsCheck.equals(b0, a0)))) {
            return false;
        }
        return true;
    }

    /**
     * Checks that all elements in {@code a} are contained in {@code b} at the
     * same position and vice versa.
     *
     * @param <T> allows to restrict the type of arguments to compare
     * @param a the first list
     * @param b the second list
     * @param equalsCheck the equality check to use
     * @return {@code true} if all elements in {@code a} are contained in
     * {@code b} at the same position and {@code b} contains no other elements,
     * {@code false} otherwise
     */
    public static <T> boolean equalsList(List<T> a, List<T> b,
            EqualsCheck<T> equalsCheck) {
        if(equalsCheck == null) {
            throw new IllegalArgumentException("equalsCheck mustn't be null");
        }
        if(a == b) {
            return true;
        }
        if(a.size() != b.size()) {
            return false;
        }
        Iterator<T> aItr = a.iterator();
        Iterator<T> bItr = b.iterator();
        while(aItr.hasNext()) {
            T aNxt = aItr.next();
            T bNxt = bItr.next();
            if(!equals(aNxt, bNxt, equalsCheck)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that all mappings between key and value in {@code a} are contained
     * in {@code b} and vice versa.
     *
     * @param <K> allows to restrict the type of keys in both maps
     * @param <V> allows to restrict the type of values in both maps
     * @param a the first map
     * @param b the second map
     * @param equalsCheckKey the equality check to use for keys
     * @param equalsCheckValue the equality check to use for values
     * @return {@code true} if the key set of {@code a} and {@code b} is equals
     * as defined by
     * {@link #equalsSet(java.util.Set, java.util.Set, de.richtercloud.equals.lambda.EqualsCheck) }
     * and all keys in {@code a} are mapped to the same values as they are in
     * {@code b}, {@code false} otherwise
     */
    public static <K,V> boolean equalsMap(Map<K,V> a, Map<K,V> b,
            EqualsCheck<K> equalsCheckKey,
            EqualsCheck<V> equalsCheckValue) {
        if(equalsCheckKey == null) {
            throw new IllegalArgumentException("equalsCheckKey mustn't be null");
        }
        if(equalsCheckValue == null) {
            throw new IllegalArgumentException("equalsCheckValue mustn't be null");
        }
        if(a == b) {
            return true;
        }
        if(a.size() != b.size()) {
            return false;
        }
        if(!equalsSet(a.keySet(), b.keySet(), equalsCheckKey)) {
            return false;
        }
        for(K key : a.keySet()) {
            if(!equals(a.get(key), b.get(key), equalsCheckValue)) {
                return false;
            }
        }
        return true;
    }
}
