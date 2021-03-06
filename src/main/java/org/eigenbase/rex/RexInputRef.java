/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package org.eigenbase.rex;

import java.util.List;

import org.eigenbase.reltype.*;
import org.eigenbase.util.Pair;

/**
 * Variable which references a field of an input relational expression.
 *
 * <p>Fields of the input are 0-based. If there is more than one input, they are
 * numbered consecutively. For example, if the inputs to a join are
 *
 * <ul>
 * <li>Input #0: EMP(EMPNO, ENAME, DEPTNO) and</li>
 * <li>Input #1: DEPT(DEPTNO AS DEPTNO2, DNAME)</li>
 * </ul>
 *
 * then the fields are:
 *
 * <ul>
 * <li>Field #0: EMPNO</li>
 * <li>Field #1: ENAME</li>
 * <li>Field #2: DEPTNO (from EMP)</li>
 * <li>Field #3: DEPTNO2 (from DEPT)</li>
 * <li>Field #4: DNAME</li>
 * </ul>
 *
 * So <code>RexInputRef(3,Integer)</code> is the correct reference for the field
 * DEPTNO2.</p>
 *
 * @author jhyde
 * @version $Id$
 * @since Nov 24, 2003
 */
public class RexInputRef
    extends RexSlot
{
    //~ Static fields/initializers ---------------------------------------------

    // list of common names, to reduce memory allocations
    private static final List<String> names = new SelfPopulatingList("$", 30);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates an input variable.
     *
     * @param index Index of the field in the underlying rowtype
     * @param type Type of the column
     */
    public RexInputRef(
        int index,
        RelDataType type)
    {
        super(
            createName(index),
            index,
            type);
    }

    //~ Methods ----------------------------------------------------------------

    /** Creates a reference to a given field in a row type. */
    public static RexInputRef of(int index, RelDataType rowType) {
        return of(index, rowType.getFieldList());
    }

    /** Creates a reference to a given field in a list of fields. */
    public static RexInputRef of(int index, List<RelDataTypeField> fields) {
        return new RexInputRef(index, fields.get(index).getType());
    }

    /** Creates a reference to a given field in a list of fields. */
    public static Pair<RexNode, String> of2(
        int index,
        List<RelDataTypeField> fields)
    {
        final RelDataTypeField field = fields.get(index);
        return Pair.of(
            (RexNode) new RexInputRef(index, field.getType()),
            field.getName());
    }

    public RexInputRef clone()
    {
        // All fields are immutable, so there's no point in creating a copy.
        return this;
    }

    public <R> R accept(RexVisitor<R> visitor)
    {
        return visitor.visitInputRef(this);
    }

    /**
     * Creates a name for an input reference, of the form "$index". If the index
     * is low, uses a cache of common names, to reduce gc.
     */
    public static String createName(int index)
    {
        return names.get(index);
    }
}

// End RexInputRef.java
