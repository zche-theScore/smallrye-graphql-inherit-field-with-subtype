/*
 * Copyright 2020 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.smallrye.graphql.bootstrap.schema.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.logging.Logger;

/**
 * Helping with collections
 * 
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
public class CollectionHelper {
    private static final Logger LOG = Logger.getLogger(CollectionHelper.class.getName());

    /**
     * Creates an empty instance of a non-interface type of collection, or a suitable subclass of
     * the interfaces {@link List}, {@link Collection}, or {@link Set}.
     * @param type the collection class
     * @return the collection
     */
    public Collection newCollection(Class type) {
        if (type.equals(Collection.class) || type.equals(List.class)) {
            return new ArrayList();
        } else if (type.equals(Set.class)) {
            return new HashSet();
        } else {
            try {
                Constructor constructor = type.getConstructor();
                constructor.setAccessible(true);
                return (Collection) constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                LOG.error("Can not create new collection of [" + type.getName() + "]", ex);
                return new ArrayList(); // default ?
            }
        }
    }
}
