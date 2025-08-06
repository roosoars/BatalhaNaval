import {
    auth,
    onAuthStateChanged,
    createUserWithEmailAndPassword,
    signInWithEmailAndPassword,
    updateProfile,
    doSignOut
} from './firebase-imports.js';
import { setup } from './game.js';

const authContainer = document.getElementById('auth-container');
const gameContainer = document.getElementById('game-container');
const loginBtn      = document.getElementById('login-btn');
const signupBtn     = document.getElementById('signup-btn');
const logoutBtn     = document.getElementById('logout-btn');

onAuthStateChanged(auth, user => {
    if (user) {
        authContainer.style.display = 'none';
        gameContainer.style.display = 'block';
        setup();
    } else {
        authContainer.style.display = 'block';
        gameContainer.style.display = 'none';
    }
});

loginBtn.addEventListener('click', () => {
    const email = document.getElementById('login-email').value;
    const pw    = document.getElementById('login-password').value;
    signInWithEmailAndPassword(auth, email, pw).catch(e => alert(e.message));
});

signupBtn.addEventListener('click', () => {
    const name  = document.getElementById('signup-name').value;
    const email = document.getElementById('signup-email').value;
    const pw    = document.getElementById('signup-password').value;

    createUserWithEmailAndPassword(auth, email, pw)
        .then(({ user }) => updateProfile(user, { displayName: name }))
        .catch(e => alert(e.message));
});

logoutBtn.addEventListener('click', () => {
    doSignOut(auth).catch(e => alert(e.message));
});
