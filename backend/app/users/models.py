from django.db import models
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager, PermissionsMixin
# Create your models here.

class UserManager(BaseUserManager):
    def create_user(self, email, password=None, **extra_fields):
        """Creates and saves a new user"""
        if not email:
            raise ValueError('Users must have an email address')
        user = self.model(email = email,**extra_fields)
        user.set_password(password)
        user.save(using=self._db)
        return user


    def create_superuser(self, email, password):
        """Creates and saves a new super user"""
        user = self.create_user(email,password)
        user.is_staff = True
        user.is_superuser = True
        user.save(using=self._db)
        return user
    
    
class User(AbstractBaseUser):
    username = models.CharField(max_length = 100 , unique=True)
    email = models.EmailField(max_length=255, unique=True)
    firstName = models.CharField(max_length = 100)
    lastName = models.CharField(max_length= 100)

    is_active = models.BooleanField(default=True)
    is_staff = models.BooleanField(default=False)
    objects = UserManager()

    USERNAME_FIELD = 'username'

    def __str__(self):
        return self.email

    def has_perm(self, perm, obj=None):
        "Does the user have a specific permission?"
        # Simplest possible answer: Yes, always
        return True

    def has_module_perms(self, app_label):
        "Does the user have permissions to view the app `app_label`?"
        # Simplest possible answer: Yes, always
        return True

    # @property
    # def is_staff(self):
    #     "Is the user a member of staff?"
    #     # Simplest possible answer: All admins are staff
    #     return self.is_admin

class PaymentCard(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    cardNum = models.CharField(max_length = 30)
    ccv = models.CharField(max_length=10)
    expiry_Date = models.CharField(max_length=25)

    def __str__(self):
        return self.cardNum


class Vehicle(models.Model):
    user = models.ForeignKey(User , on_delete=models.CASCADE)
    plateNum = models.CharField(max_length=25)
    typeOfVehicle = models.CharField(max_length=25)

    def __str__(self):
        return self.plateNum

class CarPark(models.Model):
    carparkName = models.CharField(max_length= 100)
    total_lot = models.IntegerField()
    lot_type = models.CharField(max_length=1)
    lot_available = models.IntegerField()
    carRate = models.IntegerField()
    motorcycleRate = models.IntegerField()
    heavyVehicleRate = models.IntegerField()
    postalCode = models.IntegerField()
    address = models.CharField(max_length=100)
    x= models.FloatField()
    y = models.FloatField()

    def __str__(self):
        retun self.carparkName

