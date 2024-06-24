 const currentUrlsemester = window.location.href;
 const urlsemester = new URL(currentUrlsemester);

const baseUrlsemester = urlsemester.origin;


const value= $("#select_semester_semnew").val();

const dateInput= $("#day_start_semnew").val();

const dateEndInput= $("#day_end_semnew").val();

$("#select_semester_semnew").on("change",()=>{
	
	// Use the current date instead of a fixed example date
            let date = new Date();

            // Show the date input
            dateInput.parentElement.parentElement.style.display = 'block';

            // Find the first Monday on or after the current date
            let firstMonday = new Date(date);
            let dayOfWeek = firstMonday.getUTCDay();
            if (dayOfWeek !== 1) { // 1 corresponds to Monday
                const daysUntilMonday = (8 - dayOfWeek) % 7;
                firstMonday.setDate(firstMonday.getDate() + daysUntilMonday);
            }

            let next6day = new Date(firstMonday);
            next6day.setDate(next6day.getDate() + 6);

            const min = firstMonday.toISOString().split('T')[0];
            const max = next6day.toISOString().split('T')[0];

            dateInput.setAttribute('min', min);
            dateInput.setAttribute('max', max);
     
})

$("#day_start_semnew").on("change",()=>{
	const selectedDate = new Date(this.value);
            const day = selectedDate.getUTCDay();

            if (day !== 1) { // 1 corresponds to Monday
                alert('Please select a Monday.');
                this.value = ''; // Reset the value if it's not a Monday
            }
            // Update min attribute of day_end input
            dateEndInput.setAttribute('min', this.value);
            // Reset day_end value if it's before the new min value
            if (dateEndInput.value && dateEndInput.value < this.value) {
                dateEndInput.value = '';
            }
            // Show the day_end input
            dateEndInput.parentElement.parentElement.style.display = 'block';
})

//day end
$("#day_end_semnew").on("change",()=>{
	// Validate day_end should not be before day_start
            if (dateInput.value && this.value < dateInput.value) {
                alert('Day end cannot be before day start.');
                this.value = ''; // Reset day_end value
            }
})


