let cars = [];
let employees = [];

function showTab(tabName) {
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });

    document.getElementById(`${tabName}-tab`).classList.add('active');
    event.target.classList.add('active');

    if (tabName === 'repairs') loadRepairs();
    else if (tabName === 'cars') loadCars();
    else if (tabName === 'employees') loadEmployees();
}

async function loadCars() {
    const response = await fetch('/autoservice/api/cars');
    cars = await response.json();

    const tbody = document.querySelector('#cars-table tbody');
    tbody.innerHTML = '';

    cars.forEach(car => {
        const row = `
            <tr>
                <td>${car.carId}</td>
                <td>${car.ownerId}</td>
                <td>${car.plateNumber}</td>
                <td>${car.brand}</td>
            </tr>
        `;
        tbody.innerHTML += row;
    });

    updateCarSelect();
}

async function loadEmployees() {
    const response = await fetch('/autoservice/api/employees');
    employees = await response.json();

    const tbody = document.querySelector('#employees-table tbody');
    tbody.innerHTML = '';

    employees.forEach(emp => {
        const row = `
            <tr>
                <td>${emp.employeeId}</td>
                <td>${emp.firstName}</td>
                <td>${emp.lastName}</td>
                <td>${emp.position}</td>
                <td>${emp.salary} ₽</td>
                <td>${emp.experience}</td>
            </tr>
        `;
        tbody.innerHTML += row;
    });

    updateEmployeeSelect();
}

async function loadRepairs() {
    const response = await fetch('/autoservice/api/repairs');
    const repairs = await response.json();

    const tbody = document.querySelector('#repairs-table tbody');
    tbody.innerHTML = '';

    repairs.forEach(repair => {
        const car = cars.find(c => c.carId === repair.carId);
        const employee = employees.find(e => e.employeeId === repair.employeeId);

        const carInfo = car ? `${car.brand} (${car.plateNumber})` : `ID: ${repair.carId}`;
        const empInfo = employee ? `${employee.firstName} ${employee.lastName}` : `ID: ${repair.employeeId}`;

        const row = `
            <tr>
                <td>${repair.repairId}</td>
                <td>${carInfo}</td>
                <td>${empInfo}</td>
                <td>${repair.appealDate}</td>
                <td>${repair.malfunctionDescription}</td>
                <td><button class="delete-btn" onclick="deleteRepair(${repair.repairId})">Удалить</button></td>
            </tr>
        `;
        tbody.innerHTML += row;
    });
}

function updateCarSelect() {
    const select = document.getElementById('repair-car-id');
    select.innerHTML = '<option value="">Выберите автомобиль</option>';

    cars.forEach(car => {
        const option = document.createElement('option');
        option.value = car.carId;
        option.textContent = `${car.brand} (${car.plateNumber})`;
        select.appendChild(option);
    });
}

function updateEmployeeSelect() {
    const select = document.getElementById('repair-employee-id');
    select.innerHTML = '<option value="">Выберите сотрудника</option>';

    employees.forEach(emp => {
        const option = document.createElement('option');
        option.value = emp.employeeId;
        option.textContent = `${emp.firstName} ${emp.lastName} (${emp.position})`;
        select.appendChild(option);
    });
}

document.getElementById('repair-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new URLSearchParams();
    formData.append('carId', document.getElementById('repair-car-id').value);
    formData.append('employeeId', document.getElementById('repair-employee-id').value);
    formData.append('description', document.getElementById('repair-description').value);

    await fetch('/autoservice/api/repairs', {
        method: 'POST',
        body: formData
    });

    e.target.reset();
    loadRepairs();
});

async function deleteRepair(id) {
    if (confirm('Удалить этот ремонт?')) {
        try {
            const response = await fetch(`/autoservice/api/repairs?id=${id}`, {
                method: 'DELETE'
            });

            if (response.ok || response.status === 204) {
                await loadRepairs();
            }
        } catch (error) {
            console.error('Ошибка:', error);
        }
    }
}

async function init() {
    await loadCars();
    await loadEmployees();
    await loadRepairs();
}

init();