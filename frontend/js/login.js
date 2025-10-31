// Configurazione API
const API_BASE_URL = "http://localhost:3001";

// Credenziali di test
const TEST_CREDENTIALS = {
  admin: {
    password: "admin123",
    role: "ADMIN",
    token:
      "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3NjE4NDA3OTEsImV4cCI6MTc2MjQ0NTU5MSwic3ViIjoiYWIzOWJkZjYtNmZjMi00ZDZjLWEyMjMtMjQxYjdjNDg3MzIyIn0.DGfvN1TFQRG6q5X9fpj-UKAT1Fngg-Vs_h-6pfM84WQ",
  },
  user: { password: "user123", role: "USER", token: "user-token-456" },
};

// Elementi DOM
const loginForm = document.getElementById("loginForm");
const emailInput = document.getElementById("email");
const passwordInput = document.getElementById("password");
const errorMessage = document.getElementById("error-message");

// Event listeners
loginForm.addEventListener("submit", handleLogin);

// Controlla se l'utente è già loggato
document.addEventListener("DOMContentLoaded", () => {
  const token = localStorage.getItem("token");
  if (token) {
    // Se c'è un token, reindirizza alla pagina clienti
    window.location.href = "clients.html";
  }
});

// Gestione del login
async function handleLogin(e) {
  e.preventDefault();

  const email = emailInput.value.trim();
  const password = passwordInput.value.trim();

  if (!email || !password) {
    showError("Inserisci username e password");
    return;
  }

  try {
    // Mostra loading
    const submitBtn = loginForm.querySelector('button[type="submit"]');
    const originalText = submitBtn.textContent;
    submitBtn.innerHTML = '<span class="loading"></span> Accesso in corso...';
    submitBtn.disabled = true;

    hideError();

    const testUser = TEST_CREDENTIALS[email.toLowerCase()];

    if (testUser && testUser.password === password) {
      // Login riuscito con credenziali di test
      localStorage.setItem("token", testUser.token);
      localStorage.setItem("role", testUser.role);
      localStorage.setItem("username", email);

      // Reindirizza alla pagina clienti
      window.location.href = "clients.html";
    } else {
      try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            username: email,
            password: password,
          }),
        });

        if (response.ok) {
          const data = await response.json();

          localStorage.setItem("token", data.accessToken);
          localStorage.setItem("role", "ADMIN");
          localStorage.setItem("username", email);
          window.location.href = "clients.html";
        } else {
          throw new Error("Credenziali non valide");
        }
      } catch (backendError) {
        throw new Error(
          "Credenziali non valide. Usa: admin/admin123 o user/user123"
        );
      }
    }
  } catch (error) {
    console.error("Errore durante il login:", error);
    showError(error.message || "Errore di connessione al server");
  } finally {
    const submitBtn = loginForm.querySelector('button[type="submit"]');
    submitBtn.textContent = "Accedi";
    submitBtn.disabled = false;
  }
}

function showError(message) {
  errorMessage.textContent = message;
  errorMessage.style.display = "block";
}

function hideError() {
  errorMessage.style.display = "none";
}
