import matplotlib.pyplot as plt

from numpy.random import default_rng


NUM_SAMPLES = 1000

rng = default_rng(1)

# Create samples from a normal (Gaussian) distribution
x = rng.uniform(size=(NUM_SAMPLES, NUM_SAMPLES))

plt.scatter(x[:, 0], x[:, 1])
plt.show()
