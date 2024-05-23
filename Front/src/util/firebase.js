// Import the functions you need from the SDKs you need
import { initializeApp } from 'firebase/app';
import { getStorage } from 'firebase/storage';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: 'AIzaSyAxOgKioH_7XCxEA2fYuOUfWhYik13mPDU',
  authDomain: 'starflix-bc8b0.firebaseapp.com',
  projectId: 'starflix-bc8b0',
  storageBucket: 'starflix-bc8b0.appspot.com',
  messagingSenderId: '1055415173770',
  appId: '1:1055415173770:web:8eaf09675741ce22582373',
  measurementId: 'G-L8LTQG6VJY',
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
// const analytics = getAnalytics(app)
const storage = getStorage(app);

export { storage };
