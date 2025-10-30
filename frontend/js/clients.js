// Configurazione API
const API_BASE_URL = "http://localhost:3001";

// Variabili globali
let currentPage = 0;
let pageSize = 10;
let totalPages = 0;

// Elementi DOM
const clientsTable = document.getElementById("clients-tbody");
const prevBtn = document.getElementById("prev-btn");
const nextBtn = document.getElementById("next-btn");
const pageInfo = document.getElementById("page-info");

// Event listeners
document.addEventListener("DOMContentLoaded", init);

// Inizializzazione
async function init() {
  checkAuth();
  loadClients();
}

// Controllo autenticazione
function checkAuth() {
  const token = localStorage.getItem("token");
  if (!token) {
    window.location.href = "index.html";
    return;
  }
}

// Logout
function handleLogout() {
  localStorage.clear();
  window.location.href = "index.html";
}

// Caricamento clienti
async function loadClients() {
  try {
    const token = localStorage.getItem("token");
    const response = await fetch(
      `${API_BASE_URL}/client?page=${currentPage}&size=${pageSize}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    if (response.ok) {
      const data = await response.json();
      displayClients(data.content || []);
      updatePagination(data);
    } else {
      displayClients([]);
    }
  } catch (error) {
    console.error("Errore caricamento clienti:", error);
    displayClients([]);
  }
}

// Visualizzazione clienti
function displayClients(clienti) {
  if (!clientsTable) return;

  if (clienti.length === 0) {
    clientsTable.innerHTML = `
      <tr>
        <td colspan="8" style="text-align: center; padding: 2rem; color: #666;">
          Nessun cliente trovato
        </td>
      </tr>`;
    return;
  }

  clientsTable.innerHTML = clienti
    .map(
      (cliente) => `
    <tr>
      <td>
        ${
          cliente.logoAziendale
            ? `<img src="${cliente.logoAziendale}" alt="Logo" style="width: 40px; height: 40px; object-fit: cover; border-radius: 4px;">`
            : '<div style="width: 40px; height: 40px; background: #f0f0f0; border-radius: 4px; display: flex; align-items: center; justify-content: center; font-size: 12px;">N/A</div>'
        }
      </td>
      <td>${cliente.nomeContatto || "-"}</td>
      <td>${cliente.ragioneSociale || "-"}</td>
      <td>${cliente.email || "-"}</td>
      <td>${cliente.telefono || "-"}</td>
      <td>${cliente.tipoCliente || "-"}</td>
      <td>€${
        cliente.fatturatoAnnuale
          ? cliente.fatturatoAnnuale.toLocaleString()
          : "0"
      }</td>
      <td>
        <button onclick="viewClient(${cliente.id})" class="btn-action btn-view">
          Dettagli
        </button>
      </td>
    </tr>
  `
    )
    .join("");
}

function updatePagination(data) {
  totalPages = data.totalPages || 0;
  const pageInfoElement = document.getElementById("page-info");

  if (pageInfoElement) {
    pageInfoElement.textContent = `Pagina ${currentPage + 1} di ${totalPages}`;
  }

  const prevBtn = document.getElementById("prev-btn");
  const nextBtn = document.getElementById("next-btn");

  if (prevBtn) prevBtn.disabled = currentPage === 0;
  if (nextBtn) nextBtn.disabled = currentPage >= totalPages - 1;
}

function previousPage() {
  if (currentPage > 0) {
    currentPage--;
    loadClients();
  }
}

function nextPage() {
  if (currentPage < totalPages - 1) {
    currentPage++;
    loadClients();
  }
}

// Visualizza dettagli cliente
function viewClient(clientId) {
  window.location.href = `fatture.html?clientId=${clientId}`;
}

// Event listeners per paginazione
document.addEventListener("DOMContentLoaded", () => {
  const prevBtn = document.getElementById("prev-btn");
  const nextBtn = document.getElementById("next-btn");
  const logoutBtn = document.getElementById("logout-btn");

  if (prevBtn) prevBtn.addEventListener("click", previousPage);
  if (nextBtn) nextBtn.addEventListener("click", nextPage);
  if (logoutBtn) logoutBtn.addEventListener("click", handleLogout);
});
