/**
 * RESTful UldMasterDataService<br>
 * <br>
 * Test with your browsers integrated RESTClient functionality. Firefox has a RESTClient add-on:
 * <br><br>
 * - options (OPTIONS http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService)<br>
 * <br>
 * - uldshapeExists (GET http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/uldshapeExists/LD3)<br>
 * - countUldshapes (GET http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/countUldshapes)<br>
 * - getUldshapeById (GET http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/uldshape/LD3)<br>
 * - getAllUldshapes (GET http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/uldshapes/0/8)<br>
 * <br>
 * - uldtypeExists (GET http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/uldtypeExists/PMC)<br>
 * - countUldtypes (GET http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/countUldtypes)<br>
 * - getUldtypeById (GET http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/uldtype/AKE)<br>
 * - getAllUldtypes (GET http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/uldtypes/0/8)<br>
 * - getAssignedTypes (GET http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/assignedTypes/LD3)<br>
 * - getAvailableTypes (GET http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/availableTypes/LD3)<br>
 * - assignShape (PUT http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/assignShape/LD3/AKE)<br>
 * - deassignShape (PUT http://localhost:8080/UldMasterDataService-war/rest/UldMasterDataService/deassignShape/LD3/AKE)<br>
 * <br>
 * The test data in the database support the following:<br><br>
 * - uldshapes are LD3, LD2, M6, Demi and more.<br>
 * - uldtypes are AKE, RKE, PMC and more.<br>
 */
package com.home.uldmasterdataservice;
