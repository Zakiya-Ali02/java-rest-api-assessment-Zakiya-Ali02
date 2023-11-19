    // Sample array of activities
    const activities = ['Walked to work', 'Cycled to work', 'Recycled','Mentoring','Fundraisng','Volunteering','Donating','Exercise','Balanced diet'];

    // Populate the dropdown menu with activities
    const activityDropdown = document.getElementById('activity');
    activities.forEach(activity => {
        const option = document.createElement('option');
        option.value = activity;
        option.text = activity;
        activityDropdown.add(option);
    });



    // Calculate creditpoints function
    function calculateCreditPoints() {
        const name = document.getElementById('name').value;
        const office = document.getElementById('office').value;
        const activity = document.getElementById('activity').value;

        const user = {
            name: name,
            office: office,
            activity: activity,
            creditPoints: 0 // The backend will calculate this
        };

        fetch('http://localhost:8080/api/creditpoints/calculate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
        .then(response => response.text())
        .then(data => {
            document.getElementById('result').innerText = data;
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('result').innerText = 'Error calculating credit points.';
        });
    }

    // calcualte total user point
    function getUserByName() {
    const userName = document.getElementById('userName').value;

    fetch(`http://localhost:8080/api/creditpoints/user/${userName}`)
        .then(response => response.text())
        .then(data => {
            // Remove the quotation marks from the result
            const resultElement = document.getElementById('userData');
            resultElement.innerText = data.replace(/["']/g, ''); //removes both single and double quotes
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('userData').innerText = 'Error getting user data.';
        });
}
    // calucalute total office points
    function getOfficeTotalPoints() {
        const officeName = document.getElementById('officeName').value;

        fetch(`http://localhost:8080/api/creditpoints/office/${officeName}`)
            .then(response => response.text())
            .then(data => {
                document.getElementById('officeTotalPoints').innerText = data;
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById('officeTotalPoints').innerText = 'Error getting office total points.';
            });
    }

    // gets acticty catoagry total points
    function getActivityCategoryTotalPoints() {
        const activityCategory = document.getElementById('activityCategory').value;

        fetch(`http://localhost:8080/api/creditpoints/activityCategory/${activityCategory}`)
            .then(response => response.text())
            .then(data => {
                document.getElementById('activityCategoryTotalPoints').innerText = data;
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById('activityCategoryTotalPoints').innerText = 'Error getting activity category total points.';
            });
    }

    // Sample array of activity categories
    const activityCategories = ['Climate action', 'Charity', 'Health and wellbeing'];

    // Populate the dropdown menu with activity categories
    const activityCategoryDropdown = document.getElementById('activityCategory');
    activityCategories.forEach(category => {
        const option = document.createElement('option');
        option.value = category;
        option.text = category;
        activityCategoryDropdown.add(option);
});