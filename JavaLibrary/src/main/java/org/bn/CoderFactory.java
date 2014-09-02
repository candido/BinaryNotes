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
/**
 * The main BinaryNotes package
 */
package org.bn;

/**
 * @mainpage BinaryNotes.
 * @version 1.3
 * @author Abdulla G. Abdurakhmanov (abdulla.abdurakhmanov\@gmail.com).
 * @date (c) 2006
 * 
* @section Introduction Intro The flexible opensource ASN.1 framework for Java
 * and C# (.NET)
 * @section Features - Encoding/decoding library. - The library support BER
 * (Basic Encoding Rules) and PER (Packed Encoding Rules) implementation. (Other
 * encoding such as DER,XER and Extended XER is planned in future releases). -
 * BNCompiler is the ASN.1 compiler which is able to generate Java or C# code
 * for the specified ASN.1 input file. The generated code has
 * annotations/metadata that uses the compiler in runtime. - The library use
 * only plain Java classes (POJO) and annotation without any special code
 * @section GlobalNotes Notes
 * <i>This documentation describe only API of library, without any internal
 * implementation notes.</i>
 * @section Example Example of use
 * <i> ASN.1 definition (part of definitions): </i>
 * @code
 * 	...
 *       DataSeq   ::= SEQUENCE {
 *           plain 		[0] TestPRN,
 *           unicode		[1] TestOCT OPTIONAL,
 *           binary		[2] TestOCT,
 *           simpleType	[3] PrintableString,
 *           simpleOctType   OCTET STRING,
 *           booleanType	[5] BOOLEAN,
 *           intType		[6] INTEGER,
 *           intBndType	[7] INTEGER (0 .. 255),
 *           stringArray	[8] SEQUENCE OF PrintableString,
 *           dataArray	[9] SEQUENCE OF Data,
 *           extension	ANY OPTIONAL
 *           }
 *           ...
 * @endcode
 * <i> Generated by BinaryNotes compiler code for Java: </i>
 * @code
 *   ...
 *   @ASN1Sequence ( name = "DataSeq" )
 *   public class DataSeq {
 *
 *       @ASN1Element ( name = "plain", isOptional =  false , hasTag =  true, tag = 0 )
 *       private TestPRN plain = null;
 *
 *       @ASN1Element ( name = "unicode", isOptional =  true , hasTag =  true, tag = 1 )
 *       private TestOCT unicode = null;
 *       ...
 *       @ASN1Element ( name = "booleanType", isOptional =  false , hasTag =  true, tag = 5 )
 *       @ASN1Boolean( name = "" )
 *       private Boolean booleanType = null;
 *       ...
 *       public TestPRN getPlain () {
 *           return this.plain;
 *       }
 *
 *       public void setPlain (TestPRN value) {
 *           this.plain = value;
 *       }
 *       ...
 *       public Boolean getBooleanType () {
 *           return this.booleanType;
 *       }
 *
 *       public void setBooleanType (Boolean value) {
 *           this.booleanType = value;
 *       }
 *       ...
 *   }
 * @endcode
 *
 * <i> The encoding/decoding procedure in your prorram: </i>
 *
 * @code
 *
 *   ...
 *   // Encoding for Java
 *   DataSeq seq = new DataSeq();
 *   IEncoder<DataSeq> encoder = CoderFactory.getInstance().newEncoder("BER");
 *   ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
 *   encoder.encode(seq, outputStream);
 *
 *   // Decoding for Java
 *   IDecoder decoder = CoderFactory.getInstance().newDecoder("BER");
 *   DataSeq seq = decoder.decode(stream,DataSeq.class);
 *   ...
 * @endcode
 */
import org.bn.coders.*;
import org.bn.coders.ber.*;
import org.bn.coders.der.*;
import org.bn.coders.per.*;

/**
 * The CoderFactory class is factory for creating ASN.1 encoding/decoding
 * implementations
 */
public class CoderFactory {

    private static final CoderFactory INSTANCE = new CoderFactory();

    /**
     * Access to factory (Singleton)
     *
     * @return CoderFactory instance
     */
    public static CoderFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Create new default encoder (The BER encoding is default)
     *
     * @return Encoder
     */
    public <T> IEncoder<T> newEncoder() throws Exception {
        return newEncoder("BER");
    }

    /**
     * Create new encoder for specified schema (BER, PER, PER/Aligned,
     * PER/Unaligned, ...)
     *
     * @param encodingSchema ASN.1 encoding specification
     * @return Encoder for specified specification
     * @throws Exception
     */
    public <T> IEncoder<T> newEncoder(String encodingSchema) throws Exception {
        if (encodingSchema.equalsIgnoreCase("BER")) {
            return new BEREncoder<T>();
        } else if (encodingSchema.equalsIgnoreCase("PER") || encodingSchema.equalsIgnoreCase("PER/Aligned") || encodingSchema.equalsIgnoreCase("PER/A")) {
            return new PERAlignedEncoder<T>();
        } else if (encodingSchema.equalsIgnoreCase("PER/Unaligned") || encodingSchema.equalsIgnoreCase("PER/U")) {
            return new PERUnalignedEncoder<T>();
        } else if (encodingSchema.equalsIgnoreCase("DER")) {
            return new DEREncoder<T>();
        } else {
            return null;
        }
    }

    /**
     * Create new default decoder (The BER decoding is default)
     *
     * @return
     * @throws Exception
     */
    public IDecoder newDecoder() throws Exception {
        return newDecoder("BER");
    }

    /**
     * Create new decoder for specified schema (BER, PER, PER/Aligned,
     * PER/Unaligned, ...)
     *
     * @param encodingSchema
     * @return Decoder for specified specification
     * @throws Exception
     */
    public IDecoder newDecoder(String encodingSchema) throws Exception {
        if (encodingSchema.equalsIgnoreCase("BER")) {
            return new BERDecoder();
        } else if (encodingSchema.equalsIgnoreCase("PER") || encodingSchema.equalsIgnoreCase("PER/Aligned") || encodingSchema.equalsIgnoreCase("PER/A")) {
            return new PERAlignedDecoder();
        } else if (encodingSchema.equalsIgnoreCase("PER") || encodingSchema.equalsIgnoreCase("PER/Unaligned") || encodingSchema.equalsIgnoreCase("PER/U")) {
            return new PERUnalignedDecoder();
        } else if (encodingSchema.equalsIgnoreCase("DER")) {
            return new DERDecoder();
        } else {
            return null;
        }
    }

    public IASN1PreparedElementData newPreparedElementData(Class<?> typeInfo) {
        return new ASN1PreparedElementData(typeInfo);
    }

}
