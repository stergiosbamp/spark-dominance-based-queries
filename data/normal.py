"""
This script creates and plots samples from the
normal (Gaussian) distribution
"""
import matplotlib.pyplot as plt

from numpy.random import default_rng


NUM_SAMPLES = 1000
DIM = 2

rng = default_rng(1)

# Create samples from a normal (Gaussian) distribution
x = rng.normal(size=(NUM_SAMPLES, NUM_SAMPLES, DIM))

plt.scatter(x[:, 0], x[:, 1])
plt.show()
