/*
// Licensed to DynamoBI Corporation (DynamoBI) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  DynamoBI licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at

//   http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
*/
package org.eigenbase.runtime;

import org.eigenbase.util.*;


/**
 * <code>CalcTupleIter</code> is an abstract base for iterator implementations
 * generated by {@link org.eigenbase.oj.rel.IterCalcRel} .
 */
public abstract class CalcTupleIter
    implements TupleIter
{
    //~ Instance fields --------------------------------------------------------

    protected TupleIter inputIterator;

    //~ Constructors -----------------------------------------------------------

    /**
     * Constructor.
     *
     * @param inputIterator iterator producing results to be transformed
     */
    protected CalcTupleIter(TupleIter inputIterator)
    {
        this.inputIterator = inputIterator;
    }

    //~ Methods ----------------------------------------------------------------

    // implement TupleIter
    public boolean setTimeout(long timeout, boolean asUnderflow)
    {
        return inputIterator.setTimeout(timeout, asUnderflow);
    }

    // implement TupleIter
    public boolean addListener(MoreDataListener c)
    {
        return inputIterator.addListener(c);
    }

    // implement TupleIter
    public void restart()
    {
        inputIterator.restart();
    }

    // implement TupleIter
    public StringBuilder printStatus(StringBuilder b)
    {
        return inputIterator.printStatus(b);
    }

    // implement TupleIter
    public void closeAllocation()
    {
        inputIterator.closeAllocation();
    }
}

// End CalcTupleIter.java