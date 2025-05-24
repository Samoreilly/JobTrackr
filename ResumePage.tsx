import React, { useEffect, useState, useRef } from 'react';
import axios, { AxiosError } from 'axios';
import { motion, AnimatePresence } from 'framer-motion';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ProgressBar from "./ProgressBar";
import robot from "./assets/robot.png";
import speech from "./assets/speechbbl.png";
import xicon from "./assets/xicon.png";
import bg from "./assets/bg.png";
import gsap from 'gsap';
import circle from "./assets/circle.png";

// Types
export interface ResumeData {
    experienceScore: number;
    educationScore: number;
    skillsScore: number;
    projectsScore: number;
}

interface ScoreMessage {
    text: string;
    color: string;
}

// Component
export default function ResumePage(): React.ReactElement {
    // State management
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [resumeData, setResumeData] = useState<ResumeData | null>(null);
    const [hideFile, setHideFile] = useState<boolean>(false);
    const [showSpeech, setShowSpeech] = useState<boolean>(false);
    const [showAdvice, setShowAdvice] = useState<boolean>(false);
    const [advice, setAdvice] = useState<string>('');
    const [scores, setScores] = useState({
        exp: '',
        edu: '',
        skills: '',
        pro: ''
    });
    const [adviceDone, setAdviceDone] = useState<boolean>(false);
    const [aiLoading, setAiLoading] = useState<boolean>(false);

    // Animation variants
    const containerVariants = {
        hidden: { opacity: 0, y: 20 },
        visible: { 
            opacity: 1, 
            y: 0,
            transition: { duration: 0.6, ease: "easeOut" }
        },
        exit: { 
            opacity: 0,
            y: -20,
            transition: { duration: 0.3 }
        }
    };

    // Speech synthesis effect
    useEffect(() => {
        if (adviceDone && advice) {
            const utterance = new SpeechSynthesisUtterance(advice);
            const voices = window.speechSynthesis.getVoices();
            const robotVoice = voices.find((v) => v.name === "Google UK English Male");
            
            if (robotVoice) utterance.voice = robotVoice;
            
            utterance.lang = 'en-GB';
            utterance.rate = 0.9;
            utterance.pitch = 0.6;
            
            window.speechSynthesis.speak(utterance);
            setAdviceDone(false);
        }
    }, [adviceDone, advice]);

    // File handling
    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            setSelectedFile(file);
            toast.info('File selected successfully!');
        }
    };

    // File submission
    const handleSubmit = async (): Promise<void> => {
        if (!selectedFile) {
            toast.error('Please select a file before submitting');
            return;
        }

        setIsLoading(true);
        const formData = new FormData();
        formData.append('file', selectedFile);

        try {
            const response = await axios.post('http://localhost:8080/resume', formData, {
                headers: { 'Content-Type': 'multipart/form-data' },
            });
            
            setResumeData(response.data);
            calculateMessage(response.data);
            setHideFile(true);
            toast.success('Resume analyzed successfully!');
        } catch (err) {
            const axiosError = err as AxiosError;
            toast.error(`Upload failed: ${axiosError.response?.data || 'Unknown error'}`);
            setResumeData(null);
            setHideFile(false);
        } finally {
            setIsLoading(false);
        }
    };

    // Score calculation
    const calculateMessage = (data: ResumeData) => {
        const getScoreMessage = (score: number, max: number): ScoreMessage => {
            const percentage = (score / max) * 100;
            if (percentage < 50) return { 
                text: 'Your score suggests room for growth in this area. Consider adding more relevant details.',
                color: 'text-red-500' 
            };
            if (percentage < 80) return { 
                text: "You're showing promise here! A few more specific achievements could make this even stronger.",
                color: 'text-yellow-500' 
            };
            return { 
                text: "Impressive strength in this area! Your experience really stands out.",
                color: 'text-green-500' 
            };
        };

        setScores({
            exp: getScoreMessage(data.experienceScore, 50).text,
            edu: getScoreMessage(data.educationScore, 20).text,
            skills: getScoreMessage(data.skillsScore, 30).text,
            pro: getScoreMessage(data.projectsScore, 25).text
        });
    };

    // Generate AI advice
    const handleAdvice = (data: ResumeData) => {
        setShowAdvice(true);
        setShowSpeech(true);
        setAiLoading(true);
        
        const totalScore = data.experienceScore + data.educationScore + data.skillsScore + data.projectsScore;
        const maxScore = 125; // Sum of all max scores (50 + 20 + 30 + 25)
        const percentage = (totalScore / maxScore) * 100;
        
        let advice = '';
        
        if (percentage >= 80) {
            advice = `Your resume is very impressive! Your combination of strong experience, education, and technical skills positions you well for top roles. To make your application even stronger, consider highlighting specific achievements with measurable impacts.`;
        } else if (percentage >= 60) {
            advice = `You have a solid foundation in your resume. Your ${data.experienceScore > 25 ? 'professional experience' : data.skillsScore > 15 ? 'technical skills' : 'educational background'} is particularly noteworthy. To strengthen your profile, consider adding more details about project outcomes and quantifiable achievements.`;
        } else if (percentage >= 40) {
            advice = `There's potential in your resume, but it could benefit from some enhancements. Focus on expanding your ${data.experienceScore < 25 ? 'professional experience' : data.skillsScore < 15 ? 'technical skills section' : 'project portfolio'}. Consider adding specific examples of your contributions and their impact.`;
        } else {
            advice = `Thank you for sharing your resume. To make it more competitive, consider gaining more hands-on experience through internships or personal projects. Focus on developing in-demand technical skills and documenting your achievements clearly.`;
        }
        
        setAdvice(advice);
        setAdviceDone(true);
    };

    // Animation setup
    const leftCircle = useRef<HTMLImageElement>(null);
    const middleCircle = useRef<HTMLImageElement>(null);
    const rightCircle = useRef<HTMLImageElement>(null);

    useEffect(() => {
        // Create a context to store our animations
        const ctx = gsap.context(() => {
            if (!leftCircle.current || !middleCircle.current || !rightCircle.current) return;

            // Kill any existing animations first
            gsap.killTweensOf([leftCircle.current, middleCircle.current, rightCircle.current]);

            // Create the animations with more noticeable movement
            gsap.to(leftCircle.current, {
                y: -10,
                duration: 1.5,
                repeat: -1,
                yoyo: true,
                ease: "power1.inOut",
            });

            gsap.to(middleCircle.current, {
                y: -10,
                duration: 1.5,
                repeat: -1,
                yoyo: true,
                ease: "power1.inOut",
                delay: 0.3,
            });

            gsap.to(rightCircle.current, {
                y: -10,
                duration: 1.5,
                repeat: -1,
                yoyo: true,
                ease: "power1.inOut",
                delay: 0.6,
            });
        });

        // Cleanup function
        return () => ctx.revert();
    }, []);

    return (
        <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-50 animate-scroll-bg" style={{
            backgroundImage: `url(${bg})`,
            backgroundSize: "3000px auto",  // Adjust this value based on your image size
            backgroundRepeat: "repeat-x",
            backgroundPosition: "0 0",
        }}>
            <nav className="bg-white/80 backdrop-blur-md border-b border-gray-200 sticky top-0 z-50">
                <div className="max-w-7xl mx-auto px-4 py-4">
                    <motion.span 
                        initial={{ opacity: 0, x: -20 }}
                        animate={{ opacity: 1, x: 0 }}
                        className="text-3xl font-bold bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent"
                    >
                        JobTrackr
                    </motion.span>
                </div>
            </nav>

            <div className="max-w-7xl mx-auto px-4 py-8">
                <AnimatePresence mode="wait">
                    {!hideFile ? (
                        <motion.div
                            key="upload"
                            variants={containerVariants}
                            initial="hidden"
                            animate="visible"
                            exit="exit"
                            className="text-center"
                        >
                            <h1 className="text-5xl font-bold mb-12 bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">
                                Resume Analysis
                            </h1>

                            <motion.div 
                                className="max-w-md mx-auto bg-white/80 backdrop-blur-md rounded-2xl shadow-xl p-8 border border-gray-200"
                                whileHover={{ y: -5 }}
                            >
                                <input
                                    type="file"
                                    onChange={handleFileChange}
                                    className="w-full mb-4 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
                                    accept=".pdf,.doc,.docx"
                                />
                                <motion.button
                                    whileHover={{ scale: 1.02 }}
                                    whileTap={{ scale: 0.98 }}
                                    className={`w-full py-3 px-6 rounded-xl text-white font-medium ${
                                        isLoading 
                                            ? 'bg-gray-400 cursor-not-allowed' 
                                            : 'bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700'
                                    }`}
                                    onClick={handleSubmit}
                                    disabled={isLoading}
                                >
                                    {isLoading ? 'Analyzing...' : 'Analyze Resume'}
                                </motion.button>
                            </motion.div>
                        </motion.div>
                    ) : (
                        <motion.div
                            key="results"
                            variants={containerVariants}
                            initial="hidden"
                            animate="visible"
                            exit="exit"
                            className="grid grid-cols-1 lg:grid-cols-3 gap-8"
                        >
                            {/* Results section */}
                            <div className="lg:col-span-2 bg-white/80 backdrop-blur-md rounded-2xl shadow-xl p-8 border border-gray-200">
                                <h2 className="text-3xl font-bold mb-8 bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">
                                    Analysis Results
                                </h2>
                                
                                {resumeData && <ProgressBar data={resumeData} />}
                                
                                <div className="space-y-6 mt-8">
                                    {Object.entries(scores).map(([key, value]) => (
                                        <motion.div 
                                            key={key}
                                            initial={{ opacity: 0, x: -20 }}
                                            animate={{ opacity: 1, x: 0 }}
                                            className="p-4 bg-white rounded-xl shadow-sm"
                                        >
                                            <h3 className="text-lg font-semibold capitalize mb-2">
                                                {key} Score
                                            </h3>
                                            <p className="text-gray-600">{value}</p>
                                        </motion.div>
                                    ))}
                                </div>
                            </div>

                            {/* AI Assistant section */}
                            <div className="lg:col-span-1">
                                <motion.div 
                                    className="bg-white/80 backdrop-blur-md rounded-2xl shadow-xl p-8 border border-gray-200"
                                    whileHover={{ scale: 1.02 }}
                                >
                                    <div className="text-center mb-6">
                                        <motion.img 
                                            src={robot} 
                                            alt="AI Assistant"
                                            className="w-32 h-32 mx-auto mb-4"
                                            animate={{ y: [0, -10, 0] }}
                                            transition={{ 
                                                duration: 2,
                                                repeat: Infinity,
                                                ease: "easeInOut"
                                            }}
                                        />
                                        <h3 className="text-xl font-bold text-gray-800">AI Assistant</h3>
                                    </div>

                                    {!showAdvice ? (
                                        <motion.button
                                            whileHover={{ scale: 1.02 }}
                                            whileTap={{ scale: 0.98 }}
                                            className="w-full py-3 px-6 rounded-xl bg-gradient-to-r from-blue-600 to-indigo-600 text-white font-medium"
                                            onClick={() => resumeData && handleAdvice(resumeData)}
                                        >
                                            Get AI Advice
                                        </motion.button>
                                    ) : (
                                        <motion.div
                                            initial={{ opacity: 0 }}
                                            animate={{ opacity: 1 }}
                                            className="p-4 bg-blue-50 rounded-xl"
                                        >
                                            <p className="text-gray-800">{advice}</p>
                                        </motion.div>
                                    )}
                                </motion.div>
                            </div>
                        </motion.div>
                    )}
                </AnimatePresence>
            </div>
            
            <ToastContainer 
                position="bottom-right"
                autoClose={3000}
                hideProgressBar={false}
                newestOnTop
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="light"
            />

            {!aiLoading ? (
                <div className="flex absolute left-1/2 -translate-x-1/2 bottom-1 overflow-visible">
                    <div className="flex mx-auto relative overflow-visible">
                        <img 
                            ref={leftCircle}
                            className="h-[12px] w-[12px]" 
                            src={circle} 
                            alt="grey circle animation"
                        />
                        <img 
                            ref={middleCircle}
                            className="h-[12px] w-[12px] ml-2" 
                            src={circle} 
                            alt="grey circle animation"
                        />
                        <img 
                            ref={rightCircle}
                            className="h-[12px] w-[12px] ml-2" 
                            src={circle} 
                            alt="grey circle animation"
                        />
                    </div>
                </div>
            ) : (<p className="text-white text-md">Get AI advice</p>)}
        </div>
    );
} 