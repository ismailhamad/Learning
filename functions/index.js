// The Cloud Functions for Firebase SDK to create Cloud Functions and set up triggers.
const functions = require('firebase-functions');
const nodemailer = require('nodemailer');
// The Firebase Admin SDK to access Firestore.
const admin = require('firebase-admin');

admin.initializeApp();

// BEFORE RUNNING:
// ---------------
// 1. If not already done, enable the Identity and Access Management (IAM) API
//    and check the quota for your project at
//    https://console.developers.google.com/apis/api/iam
// 2. This sample uses Application Default Credentials for authentication.
//    If not already done, install the gcloud CLI from
//    https://cloud.google.com/sdk and run
//    `gcloud beta auth application-default login`.
//    For more information, see
//    https://developers.google.com/identity/protocols/application-default-credentials
// 3. Install the Node.js client library by running
//    `npm install googleapis --save`

const {google} = require('googleapis');
const iam = google.iam('v1');

async function main () {
  const authClient = await authorize();
  const request = {
    // The `name` parameter's value depends on the target resource for the
    // request, namely
    // [`roles`](/iam/reference/rest/v1/roles),
    // [`projects`](/iam/reference/rest/v1/projects.roles), or
    // [`organizations`](/iam/reference/rest/v1/organizations.roles). Each
    // resource type's `name` value format is described below:
    // * [`roles.get()`](/iam/reference/rest/v1/roles/get): `roles/{ROLE_NAME}`.
    // This method returns results from all
    // [predefined roles](/iam/docs/understanding-roles#predefined_roles) in
    // Cloud IAM. Example request URL:
    // `https://iam.googleapis.com/v1/roles/{ROLE_NAME}`
    // * [`projects.roles.get()`](/iam/reference/rest/v1/projects.roles/get):
    // `projects/{PROJECT_ID}/roles/{CUSTOM_ROLE_ID}`. This method returns only
    // [custom roles](/iam/docs/understanding-custom-roles) that have been
    // created at the project level. Example request URL:
    // `https://iam.googleapis.com/v1/projects/{PROJECT_ID}/roles/{CUSTOM_ROLE_ID}`
    // * [`organizations.roles.get()`](/iam/reference/rest/v1/organizations.roles/get):
    // `organizations/{ORGANIZATION_ID}/roles/{CUSTOM_ROLE_ID}`. This method
    // returns only [custom roles](/iam/docs/understanding-custom-roles) that
    // have been created at the organization level. Example request URL:
    // `https://iam.googleapis.com/v1/organizations/{ORGANIZATION_ID}/roles/{CUSTOM_ROLE_ID}`
    // Note: Wildcard (*) values are invalid; you must specify a complete project
    // ID or organization ID.
    name: 'roles/my-role',  // TODO: Update placeholder value.

    auth: authClient,
  };

  try {
    const response = (await iam.roles.get(request)).data;
    // TODO: Change code below to process the `response` object:
    console.log(JSON.stringify(response, null, 2));
  } catch (err) {
    console.error(err);
  }
}
main();

async function authorize() {
  const auth = new google.auth.GoogleAuth({
    scopes: ['https://www.googleapis.com/auth/cloud-platform']
  });
  return await auth.getClient();
}

// Take the text parameter passed to this HTTP endpoint and insert it into 
// Firestore under the path /messages/:documentId/original
exports.addMessage = functions.https.onRequest(async (req, res) => {
  // Grab the text parameter.
  const original = req.query.text;
  // Push the new message into Firestore using the Firebase Admin SDK.
  const writeResult = await admin.firestore().collection('messages').add({original: original});
  // Send back a message that we've successfully written the message
  res.json({result: `Message with ID: ${writeResult.id} added.`});
});

// Listens for new messages added to /messages/:documentId/original and creates an
// uppercase version of the message to /messages/:documentId/uppercase
exports.makeUppercase = functions.firestore.document('/messages/{documentId}')
    .onCreate((snap, context) => {
      // Grab the current value of what was written to Firestore.
      const original = snap.data().original;

      // Access the parameter `{documentId}` with `context.params`
      functions.logger.log('Uppercasing', context.params.documentId, original);
      
      const uppercase = original.toUpperCase();
      
      // You must return a Promise when performing asynchronous tasks inside a Functions such as
      // writing to Firestore.
      // Setting an 'uppercase' field in Firestore document returns a Promise.
      return snap.ref.set({uppercase}, {merge: true});
    });
	
	
	var transporter = nodemailer.createTransport({
    host: 'smtp.gmail.com',
    port: 465,
    secure: true,
    auth: {
        user: 'saleemmater123@gmail.com',
        pass: 'qrzvbuuctbivmcwc'
    }
});


	
	exports.sendMailOverHTTP = functions.https.onRequest((req, res) => {
    const mailOptions = {
        from: `learing@gmail.com`,
        to: 'shroud123shroud@gmail.com',
        subject: 'Add to Course',
        html: `<h1>learing Course</h1>
                            <p>
                               <b>add course : name<br>
                               <b>des : des<br>
                               <b>Email: </b>'shroud123shroud@gmail.com'<br>
                            </p>`
    };


    return transporter.sendMail(mailOptions, (error, data) => {
        if (error) {
            return res.send(error.toString());
        }
        var data = JSON.stringify(data)
        return res.send(`Sent! ${data}`);
    });

});

	
	
