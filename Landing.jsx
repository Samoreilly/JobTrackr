import { Link } from 'react-router-dom';
import React, { useEffect, useRef, useState, useCallback } from "react";
import gsap from 'gsap';
import './App.css';
import myImage from './assets/JobTrackr.png';
import bg from './assets/jbg.png';
import blur from'./assets/blur.png';
import robot from './assets/robot.png';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { debounce } from 'lodash';

export default function Landing() {
    const leftCard = useRef(null);
    const rightCard = useRef(null);
    const thirdCenterCard = useRef(null);
    const heroSection = useRef(null);
    const navbarRef = useRef(null);
    const [isScrolled, setIsScrolled] = useState(false);
    
    gsap.registerPlugin(ScrollTrigger);

    // Handle scroll for navbar transparency
    const handleScroll = useCallback(debounce(() => {
        const scrollPosition = window.scrollY;
        setIsScrolled(scrollPosition > 50);
    }, 100), []);

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, [handleScroll]);

    // Initialize animations
    useEffect(() => {
        const isMobile = window.innerWidth < 768;
        const tl = gsap.timeline({ defaults: { ease: 'power4.out' }});

        // Navbar animation
        tl.fromTo(navbarRef.current,
            { y: -100, opacity: 0 },
            { y: 0, opacity: 1, duration: 1 }
        );

        // Hero section animation
        tl.fromTo(heroSection.current,
            { opacity: 0, y: 50 },
            { opacity: 1, y: 0, duration: 1 },
            "-=0.5"
        );

        // Cards animation
        if (isMobile) {
            tl.fromTo(leftCard.current,
                { y: '-100vh', opacity: 0 },
                { y: 0, opacity: 1, duration: 1.5, ease: 'back.out(1.2)' }
            );

            tl.fromTo(rightCard.current,
                { y: '100vh', opacity: 0 },
                { y: 0, opacity: 1, duration: 1.5, ease: 'back.out(1.2)' },
                "-=1"
            );
        } else {
            tl.fromTo(leftCard.current,
                { x: '-100vw', opacity: 0 },
                { x: 0, opacity: 1, duration: 1.5, ease: 'back.out(1.2)' }
            );

            tl.fromTo(rightCard.current,
                { x: '100vw', opacity: 0 },
                { x: 0, opacity: 1, duration: 1.5, ease: 'back.out(1.2)' },
                "-=1"
            );
        }

        // Third card scroll animation
        gsap.fromTo(
            thirdCenterCard.current,
            { y: '50vh', opacity: 0 },
            {
                y: 0,
                opacity: 1,
                duration: 1.5,
                ease: 'power4.out',
                scrollTrigger: {
                    trigger: thirdCenterCard.current,
                    start: 'top 80%',
                    end: 'top 20%',
                    toggleActions: 'play none none reverse',
                    scrub: 1
                },
            }
        );

        // Refresh ScrollTrigger after all animations are set
        ScrollTrigger.refresh();

        // Cleanup
        return () => {
            ScrollTrigger.getAll().forEach(trigger => trigger.kill());
        };
    }, []);

    return (
        <div className="overflow-x-hidden min-h-screen bg-gray-50 relative"
             style={{
                 backgroundImage: `url(${bg})`,
                 backgroundSize: "cover",
                 backgroundPosition: "center",
                 backgroundAttachment: "fixed"
             }}
        >
            {/* Navbar with transparency effect */}
            <nav 
                ref={navbarRef}
                className={`fixed top-0 left-0 right-0 z-50 transition-all duration-300 ${
                    isScrolled ? 'bg-white/90 backdrop-blur-md shadow-lg' : 'bg-transparent'
                }`}
            >
                <div className="max-w-7xl mx-auto px-4 sm:px-6 py-4 flex items-center justify-between">
                    <span className={`text-2xl md:text-3xl font-bold transition-colors duration-300 ${
                        isScrolled ? 'text-blue-600' : 'text-white'
                    }`}>
                        JobTrackr
                    </span>
                    <Link to="/home">
                        <img
                            src={myImage}
                            alt="JobTrackr Logo"
                            className="h-12 md:h-16 hover:scale-105 transition-transform duration-300"
                        />
                    </Link>
                </div>
            </nav>

            {/* Hero Section */}
            <main ref={heroSection} className="max-w-7xl mx-auto px-4 sm:px-6 py-32 md:py-40">
                <section className="text-center">
                    <h1 className="text-4xl md:text-6xl font-bold text-white mb-6 drop-shadow-lg">
                        Track, Scan, Apply - {' '}
                        <span className="bg-gradient-to-r from-blue-600 to-indigo-600 text-transparent bg-clip-text">
                            JobTrackr
                        </span>
                    </h1>
                    <p className="text-xl md:text-2xl text-white/90 mb-8 drop-shadow-md">
                        Simplifying Your Job Search Journey
                    </p>

                    <div className="flex justify-center gap-6 mt-8">
                        <Link
                            to="/signup"
                            className="px-8 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 
                                     transition-all duration-300 transform hover:scale-105 hover:shadow-lg"
                        >
                            Get Started
                        </Link>
                        <Link
                            to="/login"
                            className="px-8 py-3 bg-white/10 backdrop-blur-md text-white border-2 border-white/20 
                                     rounded-lg hover:bg-white/20 transition-all duration-300 transform hover:scale-105"
                        >
                            Login
                        </Link>
                    </div>
                </section>
            </main>

            {/* Cards section with improved layout */}
            <div className="px-4 sm:px-6 py-16">
                <div className="flex flex-col md:flex-row gap-8 justify-center max-w-6xl mx-auto">
                    {/* Left Card */}
                    <div
                        ref={leftCard}
                        className="bg-white/90 backdrop-blur-md rounded-3xl p-8 shadow-xl hover:shadow-2xl 
                                 transition-all duration-300 w-full md:w-1/2 transform hover:-translate-y-2"
                    >
                        <div className="text-gray-800 space-y-6">
                            <h2 className="text-2xl md:text-3xl font-bold text-blue-600 text-center">
                                Take Control of Your Job Hunt
                            </h2>
                            <p className="text-lg font-semibold leading-relaxed">
                                Apply to jobs, track every application, and scan your resume all in one place.
                            </p>
                            <p className="text-md leading-relaxed text-gray-600">
                                Our AI helps you improve your resume instantly, stay organized, and move faster. 
                                No clutter, no guesswork, just tools that make job searching easier.
                            </p>
                        </div>
                    </div>

                    {/* Right Card */}
                    <div
                        ref={rightCard}
                        className="bg-white/90 backdrop-blur-md rounded-3xl p-8 shadow-xl hover:shadow-2xl 
                                 transition-all duration-300 w-full md:w-1/2 transform hover:-translate-y-2"
                    >
                        <div className="text-gray-800 space-y-6">
                            <h2 className="text-2xl md:text-3xl font-bold text-blue-600 text-center">
                                Advanced AI Resume Scanner
                            </h2>
                            <p className="text-lg font-semibold leading-relaxed">
                                Get instant feedback on your resume and optimize it for specific job postings.
                            </p>
                            <p className="text-md leading-relaxed text-gray-600">
                                Our AI analyzes keywords, formatting, and content to help you stand out to 
                                recruiters and applicant tracking systems.
                            </p>
                        </div>
                    </div>
                </div>
            </div>

            {/* AI Features Section */}
            <div className="px-4 sm:px-6 py-16">
                <div
                    ref={thirdCenterCard}
                    className="bg-white/90 backdrop-blur-md rounded-3xl shadow-xl w-full md:w-3/4 mx-auto 
                             overflow-hidden transform transition-all duration-300 hover:shadow-2xl"
                >
                    <div className="relative p-8 md:p-12">
                        <img 
                            className="absolute top-4 left-4 h-[50px] w-[50px] md:h-[70px] md:w-[70px] 
                                     animate-bounce" 
                            src={robot} 
                            alt="AI Robot"
                        />

                        <div className="text-center space-y-6">
                            <h2 className="text-3xl md:text-4xl font-bold text-blue-600">
                                AI-Powered Resume Technology
                            </h2>
                            <p className="text-xl font-semibold text-gray-800">
                                Unlock your career potential with NLP technology that analyzes resumes 
                                like professional recruiters
                            </p>
                            
                            <div className="grid grid-cols-1 md:grid-cols-3 gap-6 my-12">
                                {[
                                    {
                                        title: "Experience Analysis",
                                        description: "Identifies job titles, duration, seniority, and key achievements through pattern recognition",
                                        color: "from-blue-500 to-indigo-500"
                                    },
                                    {
                                        title: "Skill Detection",
                                        description: "Matches against 250+ programming, data, web, and cloud technologies",
                                        color: "from-indigo-500 to-purple-500"
                                    },
                                    {
                                        title: "Education Scoring",
                                        description: "Evaluates degrees, fields of study, and prestigiousness of institutions",
                                        color: "from-purple-500 to-pink-500"
                                    }
                                ].map((feature, index) => (
                                    <div 
                                        key={index}
                                        className="bg-gradient-to-br p-[2px] rounded-xl hover:scale-105 transition-transform duration-300"
                                        style={{ backgroundImage: `linear-gradient(to bottom right, var(--tw-gradient-stops))` }}
                                    >
                                        <div className="bg-white h-full rounded-xl p-6">
                                            <h3 className="font-bold text-xl mb-3 bg-gradient-to-r text-transparent bg-clip-text"
                                                style={{ backgroundImage: `linear-gradient(to right, var(--tw-gradient-stops))` }}>
                                                {feature.title}
                                            </h3>
                                            <p className="text-gray-600">
                                                {feature.description}
                                            </p>
                                        </div>
                                    </div>
                                ))}
                            </div>

                            <Link to="/signup">
                                <button className="bg-gradient-to-r from-blue-600 to-indigo-600 text-white font-bold 
                                                 py-3 px-8 rounded-lg transform transition-all duration-300 
                                                 hover:scale-105 hover:shadow-lg">
                                    Get Your Resume Score
                                </button>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>

            {/* Footer */}
            <footer className="mt-12 py-8 bg-white/10 backdrop-blur-md">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 text-center text-white">
                    <p className="text-lg">© 2025 JobTrackr. All rights reserved.</p>
                </div>
            </footer>
        </div>
    );
} 