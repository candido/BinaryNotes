/*
 Copyright 2006-2011 Abdulla Abdurakhmanov (abdulla@latestbit.com)
 Original sources are available at www.latestbit.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
using System;
using System.Reflection;
using System.IO;
using org.bn.attributes;
using org.bn.attributes.constraints;
using org.bn.coders;
using org.bn.metadata;

namespace org.bn.metadata.constraints
{
    public class ASN1SizeConstraintMetadata : IASN1ConstraintMetadata {
        private long maxValue;
        
        public ASN1SizeConstraintMetadata(ASN1SizeConstraint annotation) {
            this.maxValue = annotation.Max;
        }
        
        public long Max {
            get { return this.maxValue; }
        }
        
        public bool checkValue(long value) {
            return value == maxValue;
        }
        
    }
}
