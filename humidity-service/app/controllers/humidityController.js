const { TimestreamWriteClient, WriteRecordsCommand } = require("@aws-sdk/client-timestream-write")
const writeClient = new TimestreamWriteClient({ region: "us-east-2" });

/**
 * Stores water temperature in the time series database
 * @async
 * @function
 * @param {decimal} value 
 * @param {Time} time 
 * @returns {Promise<object>} A promise object containing the water temperature data
 */
async function storeHumidity(value, time) {
    const DATABASE_NAME = "HumidityDB"
    const TABLE_NAME = "HumidityTable"
    console.log("Writing records");
    const dimensions = [
        { 'Name': 'region', 'Value': 'us-east-2' },
        { 'Name': 'az', 'Value': 'az1' },
        { 'Name': 'hostname', 'Value': 'host1' }
    ];
    const data_point = {
        'Dimensions': dimensions,
        'MeasureName': "humidity_data",
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
    const result = await writeClient.send(command);
    return result;
}

module.exports = {
    storeHumidity
};