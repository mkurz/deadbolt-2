/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.objectify.deadbolt.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class ObjectUtilsTest
{
    @Test
    public void isTrue_null()
    {
        Assert.assertFalse(ObjectUtils.isTrue(null));
    }

    @Test
    public void isTrue_objectTrue()
    {
        Assert.assertTrue(ObjectUtils.isTrue(Boolean.TRUE));
    }

    @Test
    public void isTrue_objectFalse()
    {
        Assert.assertFalse(ObjectUtils.isTrue(Boolean.FALSE));
    }

    @Test
    public void isTrue_primitiveTrue()
    {
        Assert.assertTrue(ObjectUtils.isTrue(true));
    }

    @Test
    public void isTrue_primitiveFalse()
    {
        Assert.assertFalse(ObjectUtils.isTrue(false));
    }
}
