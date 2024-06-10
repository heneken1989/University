 const notifications = document.querySelectorAll('.notification-container');

        notifications.forEach(notification => {
            const timeout = setTimeout(() => {
                notification.remove(); 
            }, 5000);

            // Add click event listener to clear the timeout if the notification is dismissed manually
            notification.addEventListener('click', () => {
                clearTimeout(timeout); 
                notification.remove(); 
            });
        });
        
        
        
        
        
        const notificationContainer = document.querySelector('.notification-container');
        const success = notificationContainer.querySelectorAll('.notification-success');
        if (success.length > 0) {
                 notificationContainer.style.transform = 'translateX(0)';
        }
        
        //