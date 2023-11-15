const { TimestreamWriteClient, WriteRecordsCommand } = require("@aws-sdk/client-timestream-write")
const writeClient = new TimestreamWriteClient({
    region: "us-east-2",
    credentials: {
        accessKeyId: "Insert access key here",
        secretAccessKey: "Insert secret access key here"
    }
});

/**
 * Stores water temperature in the time series database
 * @async
 * @function
 * @param {decimal} value 
 * @param {Time} time 
 * @returns {Promise<object>} A promise object containing the water temperature data
 */
async function storeAirTemp(value, time) {
    const DATABASE_NAME = "AirTempDB"
    const TABLE_NAME = "AirTempTable"
    console.log("Writing records");
    const dimensions = [
        { 'Name': 'region', 'Value': 'us-east-2' },
        { 'Name': 'az', 'Value': 'az1' },
        { 'Name': 'hostname', 'Value': 'host1' }
    ];
    const data_point = {
        'Dimensions': dimensions,
        'MeasureName': "air_temp_data",
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
    storeAirTemp
};