/* 
Copyright (c) 2010, NHIN Direct Project
All rights reserved.

Authors:
   Greg Meyer      gm2552@cerner.com
 
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer 
in the documentation and/or other materials provided with the distribution.  Neither the name of the The NHIN Direct Project (nhindirect.org). 
nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS 
BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.nhindirect.policy.impl;

import org.nhindirect.policy.IntegerPolicyOperatorExecutor;
import org.nhindirect.policy.PolicyOperator;
import org.nhindirect.policy.PolicyValue;
import org.nhindirect.policy.PolicyValueFactory;

/**
 * Executor that evaluates operations that take two parameters and return an integer value.
 * @author Greg Meyer
 * @since 1.0
 * 
 * @param <O1> Object type of the first operand.
 * @param <O2> Object type of the second operand.
 */
public class BinaryIntegerPolicyOperatorExecutor<O1,O2> implements IntegerPolicyOperatorExecutor<O1>
{
	protected final PolicyValue<O1> operand1;
	protected final PolicyValue<O2> operand2;	
	protected final PolicyOperator operator;
	
	public BinaryIntegerPolicyOperatorExecutor(PolicyValue<O1> operand1, PolicyValue<O2> operand2, PolicyOperator operator)
	{
		if (!(operator.equals(PolicyOperator.BITWISE_AND) || operator.equals(PolicyOperator.BITWISE_OR)))
			throw new IllegalArgumentException("Operator " + operator.getOperatorText() + " is not allowed for this executor type.");
		
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operator = operator;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PolicyValue<Integer> execute() 
	{	
		int retVal = 0;
		
		switch(operator)
		{
			case BITWISE_AND:
			case BITWISE_OR:
			{
				int int1 = (operand1.getPolicyValue() instanceof Integer) ? Integer.class.cast(operand1.getPolicyValue()) :
					Integer.valueOf(operand1.getPolicyValue().toString());
				int int2 = (operand2.getPolicyValue() instanceof Integer) ? Integer.class.cast(operand2.getPolicyValue()) :
					Integer.valueOf(operand2.getPolicyValue().toString());
				
				if (operator.equals(PolicyOperator.BITWISE_AND))
					retVal = int1 & int2;
				else 
					retVal = int1 | int2;
				
				break;
			}
			///CLOVER:OFF
			default:
				retVal = 0;
			///CLOVER:ON
		}
		return PolicyValueFactory.getInstance(retVal);
	}
	
	/**
	 * {@inheritDoc}
	 */
    ///CLOVER:OFF
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder("Operator: ").append(operator.toString())
		.append("\r\nOperand 1: ").append(operand1)
		.append("\r\nOperand 2: ").append(operand2).append("\r\n");
		
		return builder.toString();
	}
    ///CLOVER:ON
}
