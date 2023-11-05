// /**
//  * Gets all water sensor data
//  * @async
//  * @function
//  * @param {object} db - Database connection
//  * @returns {Promise<Array<object>>} A promise that resolves to an array objects containing the water sensor data
//  */
// async function getAllWaterTemp(db) {
//     const result = await db.runQuery("SELECT * FROM water_temp");
//     return result;
// }

// /**
//  * Gets the water sensor data specified by the id
//  * @async
//  * @function
//  * @param {object} db - Database connection
//  * @param {int} id - Water temperature id
//  * @returns {Promise<object>} A promise object containing the water sensor data
//  */
// async function getWaterTemp(db, id) {
//     const result = await db.runQuery(`SELECT * FROM water_temp WHERE id=${id}`);
//     return result;
// }


// /**
//  * Adds data to the water sensor
//  * @async
//  * @function
//  * @param {object} db - Database connection
//  * @param {int} id - ID for water temperature
//  * @param {decimal} temp - Water temperature in decimal value
//  * @returns {Promise<object>} A promise object containing the water sensor data
//  */
// async function addWaterTemp(db, id, temp) {
//     const result = await db.runQuery(`INSERT INTO water_temp (id, temp) VALUES(${id}, ${temp}) RETURNING *`);
//     return result;
// }

// /**
//  * Deletes data for the water sensor
//  * @async
//  * @function
//  * @param {object} db - Database connection
//  * @param {int} id - Water temperature id
//  * @returns {Promise<object>} A promise object containing the water sensor data
//  */
// async function deleteWaterTemp(db, id) {
//     const result = await db.runQuery(`DELETE FROM water_temp WHERE id=${id} RETURNING *`);
//     return result;
// }

const { TimestreamWriteClient, WriteRecordsCommand } = require("@aws-sdk/client-timestream-write")
const writeClient = new TimestreamWriteClient({ region: "us-east-2" });

// Add water temperature data point to time-series database
async function storeWaterTemp(value, time) {
    const DATABASE_NAME = "WaterTempDB"
    const TABLE_NAME = "WaterTempTable"
    console.log("Writing records");
    const dimensions = [
        { 'Name': 'region', 'Value': 'us-east-2' },
        { 'Name': 'az', 'Value': 'az1' },
        { 'Name': 'hostname', 'Value': 'host1' }
    ];
    const data_point = {
        'Dimensions': dimensions,
        'MeasureName': "water_data",
        'MeasureValue': value.toString(),
        'MeasureValueType': 'DOUBLE',
        'Time': time.toString()
    }
    const records = [data_point];
    const params = {
        DatabaseName: DATABASE_NAME,
        TableName: TABLE_NAME,
        Records: records
    };
    const command = new WriteRecordsCommand(params);
    const data = await writeClient.send(command);
}


module.exports = {
    storeWaterTemp
};